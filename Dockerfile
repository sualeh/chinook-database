# ========================================================================
# Chinook Database
# http://www.schemacrawler.com
# Copyright (c) 2020, Sualeh Fatehi <sualeh@hotmail.com>.
# All rights reserved.
# ------------------------------------------------------------------------
#
# This software is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
#
# This software and the accompanying materials are made available under
# the terms of the Eclipse Public License v1.0.
#
# The Eclipse Public License is available at:
# http://www.eclipse.org/legal/epl-v10.html
#
# ========================================================================


FROM openjdk:8-jdk-alpine

ARG CHINOOK_DATAVASE_VERSION=2.0.1

LABEL \
  "us.fatehi.chinook-database.product-version"="Chinook Database ${CHINOOK_DATAVASE_VERSION}" \
  "us.fatehi.chinook-database.website"="http://www.schemacrawler.com" \
  "us.fatehi.chinook-database.docker-hub"="https://hub.docker.com/r/schemacrawler/chinook"

LABEL "maintainer"="Sualeh Fatehi <sualeh@hotmail.com>"

# Install Graphviz
RUN \
  apk add --update --no-cache \
  bash \
  bash-completion \
  sqlite

# Copy Chinook Database distribution from the build directory
COPY \
    ./target/chinook-database-${CHINOOK_DATAVASE_VERSION}.jar /opt/chinook-database/lib/
COPY \
    ./target/dependencies/*.jar /opt/chinook-database/lib/
COPY \
    ./target/*.sqlite /opt/chinook-database/
COPY \
    ./src/docker/chinook-database-creator.sh /opt/chinook-database
# Ensure that the Chinook Database script is executable
RUN \
    chmod +rx /opt/chinook-database/chinook-database-creator.sh

# Run the image as a non-root user, "chinook"
RUN \
    adduser -u 1000 -S chinook -G users
USER chinook
WORKDIR /home/chinook

# Create aliases for Chinook Database
RUN \
    echo 'alias chinook-database-creator="/opt/chinook-database/chinook-database-creator.sh"' \
    >> /home/chinook/.bashrc
