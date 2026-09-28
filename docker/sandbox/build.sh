#!/usr/bin/env bash
# Builds this project's sandbox image — the one factory.sandbox.image names when
# the gnomes run under the container binding.
#
#   ./docker/sandbox/build.sh                 # build gf-tests-sandbox:1
#   ./docker/sandbox/build.sh my-tag:2        # build under another tag
#   ./docker/sandbox/build.sh --no-verify     # skip the offline smoke test
#
# Two things this script does that a bare `docker build` cannot:
#
#   1. It reads distributionUrl out of gradle/wrapper/gradle-wrapper.properties
#      and passes it as a build argument. The wrapper locates a baked
#      distribution by a digest OF THAT URL STRING, so the image and the clone
#      must agree on it byte for byte. Bump the wrapper, rebuild, and only the
#      last layer is rebuilt.
#   2. It assembles the build context in a temporary directory — the Dockerfile
#      and the CA drop-in — instead of pointing docker at the
#      clone. The clone holds several 13 MB jars and the build needs none of
#      them. Layer caching is unaffected: BuildKit keys COPY layers by content.
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
project_dir="$(cd "$here/../.." && pwd)"

tag="gf-tests-sandbox:1"
verify=1
for arg in "$@"; do
    case "$arg" in
        --no-verify) verify=0 ;;
        -*) echo "build.sh: unknown option $arg" >&2; exit 2 ;;
        *) tag="$arg" ;;
    esac
done

props="$project_dir/gradle/wrapper/gradle-wrapper.properties"
gradle_url="$(sed -n 's/^distributionUrl=//p' "$props" | sed 's/\\//g')"
if [[ -z "$gradle_url" ]]; then
    echo "build.sh: no distributionUrl in $props" >&2
    exit 1
fi

context="$(mktemp -d)"
trap 'rm -rf "$context"' EXIT
cp "$here/Dockerfile" "$context/"
cp -R "$here/ca" "$context/ca"

echo "building $tag"
echo "  gradle distribution: $gradle_url"
docker build \
    --build-arg "GRADLE_DISTRIBUTION_URL=$gradle_url" \
    -t "$tag" \
    "$context"

if (( verify )); then
    echo
    echo "verifying the image contract"
    # --network none is the point of the Gradle check: if the baked distribution
    # were in the wrong place, the wrapper would try to download it and fail here
    # rather than silently costing every future box a 130 MB fetch.
    docker run --rm --network none \
        -v "$project_dir:/verify:ro" -w /verify \
        "$tag" sh -c '
            set -e
            for tool in git curl gh openspec claude java; do
                command -v "$tool" >/dev/null || { echo "MISSING: $tool"; exit 1; }
            done
            [ "$(id -un)" = gnome ] && [ "$(id -u)" = 1000 ] || { echo "not running as gnome/1000"; exit 1; }
            [ -w /gnomish/work ] || { echo "/gnomish/work not writable by gnome"; exit 1; }
            [ ! -w /etc/claude-code/managed-settings.json ] || { echo "agent policy is gnome-writable"; exit 1; }
            [ ! -w /etc/gnomish ] || { echo "/etc/gnomish is gnome-writable"; exit 1; }
            java -version 2>&1 | grep -m1 version
            gh --version | head -1
            openspec --version
            # HOME is read-only here (the clone is mounted :ro), so point the
            # wrapper at a scratch project dir; the distribution lookup is what
            # is under test, not this project build.
            cd /gnomish/scratch
            cp -R /verify/gradle /verify/gradlew .
            printf "rootProject.name = \"probe\"\n" > settings.gradle
            ./gradlew --version --console=plain | grep -E "^Gradle "
        '
    echo "OK: toolchain present, ownership correct, Gradle resolves offline"
fi

echo
echo "done: $tag"
