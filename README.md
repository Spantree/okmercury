# OKMercury

A blatant plagiarism of the [OKCupid Matching Algorithm](http://www.okcupid.com/help/match-percentages) applied to 
business matchmaking at meetings and conferences.

If you're more visual, check out this [TED Talk](http://ed.ted.com/lessons/inside-okcupid-the-math-of-online-dating-christian-rudder) describing the algorithm, which was released on
Valentines Day 2013.

Part of a [48-hour hackathon](http://mpitechcon.com/hackathon/).  This project may never be complete.

## Authors

* Cedric Hurst
* Gary Turovsky


## Installation Steps

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


## License

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
