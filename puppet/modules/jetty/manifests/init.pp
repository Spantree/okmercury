class jetty {
    # group { "www-data":
    #     ensure => present
    # }

    # user { "jetty":
    #     ensure => present,
    #     groups => ["www-data"],
    #     shell => "/bin/bash",
    #     require => Group["www-data"]
    # }

    # user { "jenkins":
    #     ensure => present,
    #     groups => ["www-data"],
    #     shell => "/bin/bash",
    #     require => Group["www-data"]
    # }

    package { "jetty":
        ensure => latest,
        require => Class['java'],
    }

    service { "jetty":
        ensure => running,
        enable => true,
        hasstatus => true,
        require => [
            Package['jetty'],
            File['/etc/init.d/jetty'],
            File['/etc/default/jetty']
        ];
    }

    file { "/etc/init.d/jetty":
        ensure => file,
        group => root,
        owner => root,
        mode => 744,
        source => "puppet:///modules/jetty/jetty-service.conf",
        notify => Service["jetty"],
    }

    file { "/etc/default/jetty":
        ensure => file,
        source => "puppet:///modules/jetty/jetty-init.conf",
        group => root,
        owner => root,
        mode => 744
	}

    file { "/usr/share/jetty/collinson.properties":
        ensure => file,
        source => "puppet:///modules/jetty/collinson.properties",
        group => jetty,
        owner => jetty,
        mode => 660,
        require => Package["jetty"]
    }

    file { "/usr/share/jetty/contexts/introcloud.xml":
        ensure => file,
        source => "puppet:///modules/jetty/introcloud.xml",
        group => jetty,
        owner => jetty,
        mode => 660,
        require => Package["jetty"]
    }
}
