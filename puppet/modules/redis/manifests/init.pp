class redis {
    package { "redis-server":
        ensure => latest;
    }

    service { "redis-server":
        ensure => running,
        enable => true,
        hasstatus => true,
        require => [
            Package['redis-server'],
            File['/etc/init.d/redis-server'],
            File['/etc/redis/redis.conf']
        ];
    }

    file { "/etc/init.d/redis-server":
        owner => root,
        group => root,
        mode => 744,
        ensure => present,
        source => "puppet:///modules/redis/redis-server-init.conf",
        notify => Service["redis-server"],
    }

    file { "/etc/redis/redis.conf":
        ensure => present,
        source => "puppet:///modules/redis/redis.conf",
        notify => Service["redis-server"],
    }
}
