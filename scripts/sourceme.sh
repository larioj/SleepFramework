#!/usr/bin/env bash

export PATH=$PATH:/usr/lib/scala/bin

echo 'Installing Sbt ...'
mkdir ~/bin
curl -s https://raw.githubusercontent.com/paulp/sbt-extras/master/sbt > ~/bin/sbt \
  && chmod 0755 ~/bin/sbt

echo 'Done :)'