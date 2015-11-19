############################################################
# Dockerfile to build the AKKA Spring container images
# Based on Ubuntu
# AKKA Spring is an application that allows users to save what they eat
# Breakfast, Lunch, and Dinner
############################################################

# Set the base image to Ubuntu
FROM ubuntu

# Usage: WORKDIR /path
WORKDIR /

# File Author / Maintainer
MAINTAINER Marcellin Nshimiyimana <nmarcellin2@gmail.com>

# Update the repository sources list
RUN apt-get update
RUN apt-get dist-upgrade -y

################## BEGIN PRE-INSTALLATION ######################
RUN DEBIAN_FRONTEND=noninteractive apt-get -y dist-upgrade
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install python-software-properties
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install software-properties-common

# install git cli
RUN DEBIAN_FRONTEND=noninteractive apt-get install --yes --force-yes git

# Install java and gradle
RUN add-apt-repository ppa:webupd8team/java 
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
RUN echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections

RUN apt-get update  --yes --force-yes
RUN DEBIAN_FRONTEND=noninteractive apt-get install oracle-java8-installer --yes --force-yes

# Install gradle
RUN add-apt-repository ppa:cwchien/gradle 
RUN apt-get update  --yes --force-yes
RUN DEBIAN_FRONTEND=noninteractive apt-get install gradle --yes --force-yes 

##################### PRE-INSTALLATION END #####################

################## BEGIN INSTALLATION ######################

RUN mkdir /spring-akka-command
ADD ./spring-akka-command /spring-akka-command

RUN echo $(ls -al /)
##################### INSTALLATION END #####################

################### BEGIN APPLICATION EXECUTION #################

# Expose the default port
EXPOSE 9090

# Default port to execute the entrypoint (MongoDB)
CMD [" build bootRun"]

# Set default container command
ENTRYPOINT gradle build bootRun -p spring-akka-command

################### APPLICATION EXECUTION END ##################
