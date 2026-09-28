# Sandbox image

The Docker image the gnomes of this project run in. `./gnomish` names it as
`factory.sandbox.image` and binds `container`, so every agent round, judge vote
and command check executes in an ephemeral box built from this recipe — not on
the host.

```bash
./docker/sandbox/build.sh              # build gf-tests-sandbox:1 and verify it
./docker/sandbox/build.sh --no-verify  # build only
./docker/sandbox/build.sh my-tag:2     # another tag
```

Run it after cloning, and again whenever the Gradle wrapper version or a pinned
tool version below changes.

## What is in it, and why

| Piece | Why it is there |
|---|---|
| `git`, `curl` | the factory's image contract — the seed clone and the egress self-check probes |
| user `gnome`, uid 1000, owns `/gnomish/**` | the contract again: every in-box process runs as this user, never root |
| JDK 25 (`eclipse-temurin:25-jdk-noble`) | `build.gradle` targets Java 25 |
| Gradle 9.7.0, baked | `./gradlew test` is the build check of three stages |
| Node 24 LTS | runs the two CLIs below; Ubuntu's own Node 18 is too old for OpenSpec |
| `@anthropic-ai/claude-code` | the agent CLI the factory launches, by its plain name — the factory renders the permission mode and the MCP exclusion itself, so no wrapper is baked here |
| `@fission-ai/openspec` | `openspec validate` in the specify and archive stages |
| `gh` | the deliver stage opens the pull request with it |

Everything is version-pinned as a `--build-arg` default in the Dockerfile:
`NODE_VERSION`, `GH_VERSION`, `AGENT_CLI_PACKAGE`, `OPENSPEC_PACKAGE`,
`BASE_IMAGE`. Bump the one you mean and rebuild.

## The Gradle layer

A fresh box would otherwise fetch ~130 MB from `services.gradle.org` on its
first `./gradlew`, once per box, and that host would have to sit in the egress
allowlist. So the distribution is baked in — and deliberately in the **last**
layer, because the wrapper's version is the argument that moves most often:
bumping it rebuilds one layer, not the toolchain above it.

`build.sh` reads `distributionUrl` out of
`gradle/wrapper/gradle-wrapper.properties` and passes it as
`GRADLE_DISTRIBUTION_URL`. This matters more than it looks: the wrapper finds a
baked distribution only under a directory it derives from a digest **of that URL
string**, so the image and the clone must agree on it byte for byte. Never hand
the Dockerfile a URL typed by hand.

The build's verification step runs `./gradlew --version` with `--network none`.
If the baked distribution were in the wrong place the wrapper would try to
download it, and that run fails loudly — instead of every future box quietly
paying for the download.

## Ownership

The image keeps the surfaces a hostile round must not be able to rewrite out of
the gnome's reach: `/etc/gnomish` (the Maven proxy/mirror settings),
`/etc/claude-code/managed-settings.json` (the agent policy) is root-owned, and
`/etc/gnomish` is root-owned as a whole directory — a root-owned file inside a writable directory could
simply be unlinked. `build.sh` asserts all of this after every build.

## The CA seam

`ca/` is imported into both the system trust store and the JVM `cacerts` at
build time. The factory does not intercept TLS today, so the directory is empty
— the seam means that change is a rebuild with one file rather than a redesign.

## What the image cannot do

No Docker inside the box: Testcontainers-style tests cannot run there. The
factory's supported route is a CI `external` check. See the factory's
`docs/guides/operator-guide-sandbox.md` for the full ladder.
