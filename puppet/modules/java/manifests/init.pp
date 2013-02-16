class java() {
	exec { "java-webupd8team-apt-key":
	    path => "/bin:/usr/bin",
	    command => "apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EEA14886",
	    unless => "apt-key list | grep EEA14886",
  	}

  	exec { "java-webupd8team-apt-repo":
	    path => "/bin:/usr/bin",
	    command => "echo 'deb http://ppa.launchpad.net/webupd8team/java/ubuntu precise main' >> /etc/apt/sources.list",
	    unless => "cat /etc/apt/sources.list | grep webupd8team",
	    require => Exec["java-webupd8team-apt-key"],
	}

	exec { "java-webupd8team-apt-update":
	    path => "/bin:/usr/bin",
	    command => "apt-get update",
	    unless => "ls /usr/bin | grep \"java$\"",
	    require => Exec["java-webupd8team-apt-repo"],
	}

	package { "debconf-utils":
	   ensure => latest,
	}

	exec { "see-license":
	    path => "/bin:/usr/bin",
	    command => "echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections",
	    unless => "sudo debconf-get-selections | grep accepted-oracle-license-v1-1",
	    require => Package["debconf-utils"],
	}

	exec { "accept-license":
	    path => "/bin:/usr/bin",
	    command => "echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections",
	    unless => "sudo debconf-get-selections | grep accepted-oracle-license-v1-1",
	    require => Package["debconf-utils"],
	}

	package { "oracle-java7-installer":
	   ensure => latest,
	   require => [Exec["java-webupd8team-apt-update"], Exec["see-license"],Exec["accept-license"]]
	}

	file { "/etc/profile.d/set_java_home.sh":
		ensure => file,
        group => root,
        owner => root,
        mode => 744,
    	source => "puppet:///modules/java/set_java_home.sh",
    	require => Package["oracle-java7-installer"]
	}

}