# Sandbox image for this project's gnomish-factory container binding.
#
# Derived from the factory's reference recipe (gnomish-factory
# docs/examples/sandbox-image/Dockerfile) and extended with this project's own
# toolchain: JDK 25, the Gradle distribution its wrapper asks for, the OpenSpec
# CLI the specify/archive stages verify with, and `gh` for the deliver stage.
#
# The factory's image contract (container adapter + egress self-check assume it):
#   - git and curl on PATH (curl runs the self-check probes)
#   - a non-root user with uid 1000 named `gnome` owning /gnomish/**
#   - injection-persistence surfaces root-owned, so a hostile round cannot
#     rewrite the rules of its own cage
#
# Layer order is deliberate: slow-moving toolchain first, the Gradle
# distribution LAST. Bumping GRADLE_DISTRIBUTION_URL then rebuilds one layer
# instead of the whole image — and that is the argument that moves most often,
# since gradle/wrapper/gradle-wrapper.properties changes on its own schedule.
ARG BASE_IMAGE=eclipse-temurin:25-jdk-noble
FROM ${BASE_IMAGE}

ARG NPM_REGISTRY=https://registry.npmjs.org/
ARG MAVEN_MIRROR_URL=https://repo.maven.apache.org/maven2
ARG AGENT_CLI_PACKAGE=@anthropic-ai/claude-code@2.1.272
ARG OPENSPEC_PACKAGE=@fission-ai/openspec@1.13.0
ARG GH_VERSION=2.100.0
# Ubuntu noble ships Node 18, which is too old for the OpenSpec CLI (it uses
# import attributes). The official LTS tarball is pinned instead.
ARG NODE_VERSION=24.21.0
# The egress guard's stable network alias on the task network; matches the
# factory's GuardCommands alias.
ARG PROXY_HOST=gnomish-guard
ARG PROXY_PORT=8080
# Provided by BuildKit; selects the gh release asset.
ARG TARGETARCH

# Toolchain: git + curl are the image contract; unzip unpacks the Gradle
# distribution baked in below; xz-utils unpacks the Node tarball.
RUN apt-get update \
 && apt-get install -y --no-install-recommends git curl ca-certificates unzip xz-utils \
 && rm -rf /var/lib/apt/lists/*

# Node runs the agent and OpenSpec CLIs.
RUN set -eu; \
    case "${TARGETARCH}" in \
      amd64) node_arch=x64 ;; \
      arm64) node_arch=arm64 ;; \
      *) echo "unsupported TARGETARCH: ${TARGETARCH}" >&2; exit 1 ;; \
    esac; \
    url="https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-${node_arch}.tar.xz"; \
    curl -fsSL "$url" -o /tmp/node.tar.xz; \
    tar -xJf /tmp/node.tar.xz -C /usr/local --strip-components=1 \
        --exclude CHANGELOG.md --exclude LICENSE --exclude README.md; \
    rm /tmp/node.tar.xz; \
    node --version

# The deliver stage runs `gh` in its round and in its own command checks. The
# release tarball is pinned; apt's GitHub repo would be an unpinned channel.
RUN set -eu; \
    url="https://github.com/cli/cli/releases/download/v${GH_VERSION}/gh_${GH_VERSION}_linux_${TARGETARCH}.tar.gz"; \
    curl -fsSL "$url" -o /tmp/gh.tar.gz; \
    tar -xzf /tmp/gh.tar.gz -C /tmp; \
    install -m 0755 "/tmp/gh_${GH_VERSION}_linux_${TARGETARCH}/bin/gh" /usr/local/bin/gh; \
    rm -rf /tmp/gh.tar.gz "/tmp/gh_${GH_VERSION}_linux_${TARGETARCH}"

# CLIs baked at build time — the box has no route to any registry at run time
# (the guard default-denies), so nothing may rely on in-box installs.
RUN npm install -g --registry "${NPM_REGISTRY}" "${AGENT_CLI_PACKAGE}" "${OPENSPEC_PACKAGE}"

# Factory CA seam: any *.pem / *.crt dropped into ca/ next to this Dockerfile is
# imported into the system and JVM trust stores. The factory does not intercept
# TLS yet, so ca/ may stay empty — the seam means that change is a rebuild.
COPY ca/ /tmp/gnomish-ca/
RUN set -e; \
    for cert in /tmp/gnomish-ca/*.pem /tmp/gnomish-ca/*.crt; do \
      [ -f "$cert" ] || continue; \
      b="$(basename "$cert")"; \
      cp "$cert" "/usr/local/share/ca-certificates/${b%.*}.crt"; \
      keytool -importcert -noprompt -cacerts -storepass changeit \
        -alias "gnomish-${b%.*}" -file "$cert"; \
    done; \
    update-ca-certificates; \
    rm -rf /tmp/gnomish-ca

# The in-box task user: uid 1000, owns the adapter's two file-channel roots and
# the Gradle home baked below. Ubuntu-family bases ship a uid-1000 `ubuntu`
# user, so an existing uid 1000 is renamed rather than duplicated.
RUN if getent passwd 1000 >/dev/null; then \
      old="$(getent passwd 1000 | cut -d: -f1)"; \
      usermod -l gnome -d /home/gnome -m "$old"; \
      groupmod -n gnome "$(getent group "$(id -g gnome)" | cut -d: -f1)"; \
    else \
      useradd -m -u 1000 gnome; \
    fi \
 && mkdir -p /gnomish/work /gnomish/scratch /opt/gradle-home \
 && chown -R gnome:gnome /gnomish /opt/gradle-home

# Root-owned build configs. The JVM ignores proxy environment variables, so
# Gradle/Maven builds reach the guard only through baked system properties.
# /etc/gnomish is root-owned end to end — a root-owned file inside a
# gnome-writable directory could simply be unlinked, so the directory is the
# surface.
RUN mkdir -p /etc/gnomish/maven \
 && printf '%s\n' \
    '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0">' \
    '  <proxies>' \
    "    <proxy><id>gnomish</id><active>true</active><protocol>http</protocol><host>${PROXY_HOST}</host><port>${PROXY_PORT}</port></proxy>" \
    "    <proxy><id>gnomish-s</id><active>true</active><protocol>https</protocol><host>${PROXY_HOST}</host><port>${PROXY_PORT}</port></proxy>" \
    '  </proxies>' \
    '  <mirrors>' \
    "    <mirror><id>gnomish-mirror</id><mirrorOf>central</mirrorOf><url>${MAVEN_MIRROR_URL}</url></mirror>" \
    '  </mirrors>' \
    '</settings>' \
    > /etc/gnomish/maven/settings.xml \
 && chown -R root:root /etc/gnomish \
 && chmod -R a+rX,go-w /etc/gnomish

# Agent-CLI policy surface: managed settings override anything the gnome writes
# under its own home, and the directory is root-owned.
RUN mkdir -p /etc/claude-code \
 && printf '{}\n' > /etc/claude-code/managed-settings.json \
 && chown -R root:root /etc/claude-code \
 && chmod 0755 /etc/claude-code && chmod 0644 /etc/claude-code/managed-settings.json

# Set before the layer that uses it; the proxy environment below is deliberately
# NOT set yet — at build time there is no guard container to proxy through, and
# the distribution download would fail resolving the alias.
ENV GRADLE_USER_HOME=/opt/gradle-home

# --- volatile layer: the Gradle distribution -------------------------------
# `./gradlew` is what every stage's build check runs, and in a fresh box it
# would fetch ~130 MB from services.gradle.org on the first invocation — a
# download per box and one more host in the egress allowlist. Baking it here
# removes both.
#
# The wrapper finds a distribution only under the exact directory it derives
# from the distribution URL: <GRADLE_USER_HOME>/wrapper/dists/<zip name without
# .zip>/<hash>/, where <hash> is Gradle's own PathAssembler digest — the MD5 of
# the URL string as an unsigned BigInteger in base 36 — plus a `<zip name>.ok`
# marker. So the URL must be byte-identical to the one in
# gradle/wrapper/gradle-wrapper.properties; build.sh reads it from there rather
# than repeating it.
ARG GRADLE_DISTRIBUTION_URL=https://services.gradle.org/distributions/gradle-9.7.0-bin.zip
RUN set -eu; \
    zip_name="$(basename "${GRADLE_DISTRIBUTION_URL}")"; \
    hash="$(node -e 'const c=require("crypto");process.stdout.write(BigInt("0x"+c.createHash("md5").update(process.argv[1]).digest("hex")).toString(36))' "${GRADLE_DISTRIBUTION_URL}")"; \
    dist_dir="${GRADLE_USER_HOME}/wrapper/dists/${zip_name%.zip}/${hash}"; \
    mkdir -p "$dist_dir"; \
    curl -fsSL "${GRADLE_DISTRIBUTION_URL}" -o /tmp/gradle.zip; \
    unzip -q /tmp/gradle.zip -d "$dist_dir"; \
    rm /tmp/gradle.zip; \
    touch "$dist_dir/${zip_name}.ok"; \
    chown -R gnome:gnome "${GRADLE_USER_HOME}"

# Runtime environment (the image, not the factory, supplies the box env). ENV is
# fixed at container creation — an in-box process cannot persist a change into
# later execs. Proxy spellings for both tool families; JVM system properties
# because the JVM ignores the env-var spellings.
ENV HTTP_PROXY=http://${PROXY_HOST}:${PROXY_PORT} \
    HTTPS_PROXY=http://${PROXY_HOST}:${PROXY_PORT} \
    http_proxy=http://${PROXY_HOST}:${PROXY_PORT} \
    https_proxy=http://${PROXY_HOST}:${PROXY_PORT} \
    JAVA_TOOL_OPTIONS="-Dhttp.proxyHost=${PROXY_HOST} -Dhttp.proxyPort=${PROXY_PORT} -Dhttps.proxyHost=${PROXY_HOST} -Dhttps.proxyPort=${PROXY_PORT}" \
    GRADLE_OPTS="-Dhttp.proxyHost=${PROXY_HOST} -Dhttp.proxyPort=${PROXY_PORT} -Dhttps.proxyHost=${PROXY_HOST} -Dhttps.proxyPort=${PROXY_PORT}" \
    MAVEN_ARGS="-s /etc/gnomish/maven/settings.xml" \
    CLAUDE_CODE_DISABLE_NONESSENTIAL_TRAFFIC=1

USER gnome
WORKDIR /gnomish/work
