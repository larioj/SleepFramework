#/bin/bash

echo 'Install Starting'

echo 'Installing Git ...'
yum install -y git

echo 'Installing wget ...'
yum install -y wget

echo 'Installing curl ...'
yum install -y curl

echo 'Installing Java ...'
yum install -y open java-1.8.0-openjdk
javac -version

echo 'Installing Scala ...'
scala="scala-2.11.8"
wget http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz
tar xvf ${scala}.tgz
sudo mv $scala /usr/lib
sudo ln -s /usr/lib/$scala /usr/lib/scala
export PATH=$PATH:/usr/lib/scala/bin
scala -version

echo 'Installing Sbt ...'
curl -s https://raw.githubusercontent.com/paulp/sbt-extras/master/sbt > ~/bin/sbt \
  && chmod 0755 ~/bin/sbt

echo 'Done :)'
