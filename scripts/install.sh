#/bin/bash

echo 'Install Starting'

echo 'Installing Git ...'
yum install -y git

echo 'Installing wget ...'
yum install -y wget

echo 'Installing curl ...'
yum install -y curl

echo 'Installing Java ...'
yum install java-1.8.0-openjdk-devel
javac -version

echo 'Installing Scala ...'
scala="scala-2.11.8"
wget http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz
tar xvf ${scala}.tgz
mv $scala /usr/lib
ln -s /usr/lib/$scala /usr/lib/scala

echo 'Installing Sbt ...'
mkdir ~/bin
curl -s https://raw.githubusercontent.com/paulp/sbt-extras/master/sbt > ~/bin/sbt \
  && chmod 0755 ~/bin/sbt

echo 'Done :)'
