class mongodb {
    package { "mongodb":
        ensure => latest;
    }

    service { "mongodb":
        ensure => running,
        enable => true,
        hasstatus => true,
        require => [
            Package['mongodb'],
            File['/etc/init/mongodb.conf'],
            File['/etc/mongodb.conf']
        ];
    }

    file { "/etc/init/mongodb.conf":
        ensure => file,
        source => "puppet:///modules/mongodb/mongodb-init.conf",
        notify => Service["mongodb"],
    }

	file { "/etc/mongodb.conf":
		ensure => file,
		source => "puppet:///modules/mongodb/mongodb.conf",
		notify => Service["mongodb"],
	}
}
