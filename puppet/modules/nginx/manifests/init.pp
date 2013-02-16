class nginx {
    package { "nginx":
        ensure => latest
    }

    service { "nginx":
        ensure => running,
        enable => true,
        hasstatus => true,
        require => [
            Package['nginx'],
            File['/etc/init.d/nginx'],
            File['/etc/nginx/nginx.conf']
        ];
    }

    file { "/etc/init.d/nginx":
        ensure => present,
        group => root,
        owner => root,
        mode => 744,
        source => "puppet:///modules/nginx/nginx-init.conf",
        notify => Service["nginx"],
    }

    file { "/etc/nginx":
        ensure => directory
    }

	file { "/etc/nginx/nginx.conf":
        ensure => file,
        source => "puppet:///modules/nginx/nginx.conf",
        require => File['/etc/nginx'],
        notify => Service["nginx"],
	}

    file { "/usr/share/nginx/www":
        ensure => directory
    }

    file { "/usr/share/nginx/www/error.html":
        ensure => file,
        source => "puppet:///modules/nginx/error.html",
        require => File['/usr/share/nginx/www'],
        notify => Service["nginx"]
    }
}