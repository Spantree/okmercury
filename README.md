# OKMercury

A blatant plagiarism of the [OKCupid Matching Algorithm](http://www.okcupid.com/help/match-percentages) applied to 
business matchmaking at meetings and conferences.  For more info, see the 
[TED Talk](http://ed.ted.com/lessons/inside-okcupid-the-math-of-online-dating-christian-rudder) they released on Valentines
Day 2013.

This project was originally part of a [48-hour hackathon](http://mpitechcon.com/hackathon/) sponsored by [Meeting
Professionals International](http://www.mpiweb.org/Home).

Also be sure to check out the live demo at http://okmercury.co (login as any username with any password).

## Authors

**Cedric Hurst:** Principal & Lead Software Engineer at [Spantree]  
**Gary Turovsky:** Senior Software Engineer at [Spantree] & Smartypants Ph.D. Candidate at [UIC]

## Mythology Lesson

In greek mythology, **Cupid** is the god of desire, attraction, and affection.

**Mercury** is the god of financial gain, commerce, eloquence, messages/communication, travelers and luck.

## How it works

Users submit questions they’d like answered by other attendees.

![okmercury-5](https://f.cloud.github.com/assets/530343/166279/cf637856-7996-11e2-908a-f501d5df01dc.png)

Other attendees answer the question for themselves and specify possible answers for the people they’d like to meet.

![okmercury-6](https://f.cloud.github.com/assets/530343/166284/1ff9e4a8-7997-11e2-91a8-84adf259e2dc.png)

Once an attendee is finished answering questions, they can add more or view their matches.

![okmercury-7](https://f.cloud.github.com/assets/530343/166285/2ef831d0-7997-11e2-974d-ff191073682e.png)

We do do a bunch of math and recommend other attendees to meet at the event.

![okmercury-8](https://f.cloud.github.com/assets/530343/166287/43f82e82-7997-11e2-8faa-05d96a19859c.png)

## Hackathon Presentation

For more details, see the presentation we gave during the hackathon, available in
[Keynote](https://github.com/Spantree/okmercury/blob/develop/docs/mpi-presentation.key?raw=true) or
[PDF](https://github.com/Spantree/okmercury/blob/develop/docs/mpi-presentation.pdf?raw=true).

## Installation Steps

If you want to hack on this project yourself, here's how to get going...

### Development Environment (IDE)

#### Download and install GGTS 3.2.x

http://www.springsource.org/downloads/sts-ggts

#### Install GGTG plugins

You can also install the Git plugin if you like, but we mostly use the command line and SourceTree.

### Local Host Environment

We use Vagrant to load all the needed infrastructure onto a virtual machine which can be updated or refreshed at any
time.  For this project, Vagrant will provision a clean Ubuntu 12.04 image, install the Oracle JDK 7, Grails, MongoDB,
and Jetty. If you're having trouble running the application on your host machine (wrong version of Java, conflicting 
MongoDB versions, etc), the Vagrant instance will provide an isolated sandbox

You'll need to follow the steps below to get your environment ready.

#### Tools You'll Need

Install the following tools to bootstrap your environment

* Install [Git](https://help.github.com/articles/set-up-git)
* Install [VirtualBox](https://www.virtualbox.org/)
* Install [Vagrant](http://www.vagrantup.com/)

#### Modify your host file

We point various services to the virtual machine via host entries.  Your `/etc/hosts` on your host machine should 
include the following:

```
192.168.80.100  dev.okmercury.com.local
```

#### Clone this repository

From the command line, clone this repository with:

```bash
git clone --recursive git@github.com:Spantree/okmercury.git
```

If you're new to git and run into trouble with this step, it might be due to missing 
[github keys](https://help.github.com/articles/generating-ssh-keys).

#### Set up your vagrant instance

Then initialize your vagrant instance with:

```bash
cd okmercury
vagrant up
```

![okmercury bash IR_Black 272 80 1](https://f.cloud.github.com/assets/530343/167615/a1070f9e-79dd-11e2-81d5-c213fe8d3db7.png)

This command may take a long time.  On the first run, Vagrant has to download a pristine Ubuntu 12.04 image and install 
all the infrastructural dependencies to run the app (via Puppet).  On subsequent runs, it will just verify that the 
virtual machine is consistent with the latest puppet manifests, so that should take less time.

#### SSH into the Vagrant instance

Run `vagrant ssh`

#### Start the grails app

From the vagrant instance shell, run:

```bash
cd /src/okmercury
grails run-app
```

This command may also take a long time on the first run.  On a fresh box, Grails will download all our Java library 
dependencies before compiling the project code.  On subsequent runs, these libraries will be cached.

#### Visit your local site

Point your browser to `http://dev.okmercury.com.local`

If that DNS doesn't resolve, make sure you've edited your `/etc/hosts` file as mentioned above.

#### Bootstrap your environment

We have a few canned questions to get you started.  To replace any existing questions, answers and matches with demo 
entries, point your browser to `http://dev.mercury.com.local/reset`

You can reset the environment at any time by visting this address again.

#### Stay to-to-date

As mentioned, we may be altering the vagrant configuration up until the time of the presentation, so make sure you have 
the latest changes by doing the following from your host terminal:

```
git pull
vagrant reload
```

#### Shut down vagrant

When you're all done elasticsearching, you can gracefull shut down your vagrant instance by running:

```
vagrant halt
```

This will close the VM.

#### Remove the virtual machine from disk

If you want to conserve disk space, you can get rid of the disk images at `~/.vagrant.d` 
and `~/VirtualBox VMs`

This repo will here for you should you need it again.

## Special Thanks

Special Thanks to Colleen Crone and Matt Dabney for helping us come up with example questions.

## Disclaimer

This project is in now way affiliated with OKCupid or its parent company, Humor Rainbow Inc. The likeness to the "OKCupid" 
name and borrowed design elements from the OKCupid site are entirely tongue-in-cheek.

## Copyright and license

Copyright 2013, Cedric Hurst and Gary Turovsky.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

[Spantree]: http://www.spantree.net
[UIC]: http://www.cs.uic.edu/

## Show us some love

Email info@spantree.net if you run into issues.  We'd be happy to help.

OKCupid people, please feel free to send any and all cease and desist letters to cease.and.desist@spantree.net as well.
