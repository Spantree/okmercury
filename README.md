# OKMercury

A blatant plagiarism of the [OKCupid Matching Algorithm](http://www.okcupid.com/help/match-percentages) applied to 
business matchmaking at meetings and conferences.

If you're more visual, check out this [TED Talk](http://ed.ted.com/lessons/inside-okcupid-the-math-of-online-dating-christian-rudder) describing the algorithm, which was released on
Valentines Day 2013.

This project was originally part of a [48-hour hackathon](http://mpitechcon.com/hackathon/) sponsored by Meeting
Planners International.

Check out the live demo at http://okmercury.co.

## Authors

* Cedric Hurst
* Gary Turovsky

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

## Installation Steps

If you want to hack on this project, here's how to get going...

## Development Environment (IDE)

#### Download and install STS 3.2.x

http://www.springsource.org/downloads/sts-ggts

#### Install the Groovy, Grails and Gradle plugins

You can also install the Git plugin if you like, but we mostly use the command line and SourceTree.

## Local Host Environment

We use Vagrant to load all the needed infrastructure onto a virtual machine which can be updated or refreshed at any
time.  You'll need to follow the steps below to get your environment ready.

#### Install Grails

http://www.grails.org

#### Install VirtualBox

https://www.virtualbox.org

#### Install Vagrant

http://www.vagrantup.com

#### Modify your host file

We point various services to the virtual machine via host entries.  Your `/etc/hosts` should include the following:

```
192.168.80.100  dev.okmercury.com.local
```

#### Start the VM

Run `vagrant up`


#### Start the local grails app

Run `grails run-app`


## Copyright and license

Copyright 2013 Cedric Hurst and Gary Turovsky.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
