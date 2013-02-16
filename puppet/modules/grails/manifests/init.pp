class grails(
	$grails_version = "2.2.0",
	$grails_bin_root = "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS",
	$grails_bin_file = "grails-2.2.0.zip",
	$grails_target_path = "/usr/share/grails"
) {
	exec { "fetch-grails":
		command => "wget -O /tmp/$grails_bin_file $grails_bin_root/$grails_bin_file",
		path => ["/bin", "/usr/bin", "/usr/sbin"],
		unless => "ls /tmp | grep ${grails_bin_file}"
	}

	exec { "extract-grails":
		command => "unzip -n $grails_bin_file",
		path => ["/bin", "/usr/bin", "/usr/sbin"],
		cwd => "/tmp",
		require => Exec["fetch-grails"],
		unless => "ls /tmp | grep \"grails-${grails_version}\$\""
	}

	file { "/usr/share/grails":
		ensure => directory
	}

	exec { "copy-grails":
		command => "cp -Rf /tmp/grails-${grails_version} /usr/share/grails/${grails_version}",
		path => ["/bin", "/usr/bin", "/usr/sbin"],
		require => Exec["extract-grails"],
		unless => "ls /usr/share/grails | grep ${grails_version}"
	}

	file { "/usr/share/grails/default":
		ensure => link,
		target => "/usr/share/grails/${grails_version}",
		require => Exec["copy-grails"]
	}

	file { "/etc/profile.d/set_grails_home.sh":
		ensure => file,
        group => root,
        owner => root,
        mode => 744,
    	source => "puppet:///modules/grails/set_grails_home.sh",
    	require => [
    		Exec["copy-grails"],
    		File["/usr/share/grails/default"]
    	]
	}
}