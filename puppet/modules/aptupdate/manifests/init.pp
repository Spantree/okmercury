class aptupdate {
	exec { "apt-get update":
		command => "/usr/bin/apt-get update",
		# onlyif => "/bin/sh -c '[ ! -f /var/cache/apt/pkgcache.bin ] || /usr/bin/find /etc/apt/* -cnewer /var/cache/apt/pkgcache.bin | /bin/grep . > /dev/null'",
		require => [
			Exec["add-10gen-key"],
			File["/etc/apt/sources.list.d/10gen.list"]
		]
	}

	exec { "add-10gen-key":
		command => "/usr/bin/apt-key adv --keyserver keyserver.ubuntu.com --recv 7F0CEB10",
		unless => "/bin/sh -c '/usr/bin/apt-key list | /bin/grep 7F0CEB10'"
	}

	file { "/etc/apt/sources.list.d/10gen.list":
		ensure => file,
        group => root,
        owner => root,
        mode => 644,
    	source => "puppet:///modules/aptupdate/10gen.list",
	}
}