class zip {
	package { "zip":
		ensure => present,
		provider => apt
	}

	package { "unzip":
		ensure => present,
		provider => apt
	}
}