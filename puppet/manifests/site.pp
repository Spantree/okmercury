node default {
	stage { 'first': 
      before => Stage['main'],
    }
    stage { 'last': }
    Stage['main'] -> Stage['last']

	class { "aptupdate": stage => "first" }
	class { "zip": }

	# Virtual Machines
	class { "java": }

	# Middleware
	class { "mongodb": }
	class { "redis": }

	# Servers
	class { "nginx": }
	class { "grails": stage => "last" }
	class { "jetty": }
}
