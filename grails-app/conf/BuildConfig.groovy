grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
        excludes "hibernate", "grails-hibernate"
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()
		
		mavenRepo "http://maven.springframework.org/release"
		mavenRepo "http://maven.springframework.org/snapshot"
		mavenRepo "http://maven.springframework.org/milestone"
		
		// Repo for Spantree Artifactory
		mavenRepo "http://spantree.artifactoryonline.com/spantree/plugins-releases-local"

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        runtime 'org.codehaus.gpars:gpars:1.0.0'
		runtime 'org.codehaus.groovy.modules.http-builder:http-builder:0.6'
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
		
		// Spring Social Dependencies
		def springSocialVersion = "1.0.2.RELEASE"
		
		compile("org.springframework.social:spring-social-core:${springSocialVersion}") { transitive = false }
		compile("org.springframework.social:spring-social-web:${springSocialVersion}") { transitive = false }
		compile("org.springframework.social:spring-social-facebook:${springSocialVersion}") { transitive = false }
		compile("org.springframework.social:spring-social-twitter:${springSocialVersion}") { transitive = false }
		compile("org.springframework.social:spring-social-linkedin:1.0.0.RC1") { transitive = false }
		compile("org.springframework.security:spring-security-crypto:3.1.0.RELEASE") { transitive = false }
		compile("javax.inject:javax.inject:1")
    }

    plugins {
        // runtime ":hibernate:$grailsVersion"
        runtime(":jquery:1.8.3") { excludes "hibernate"}
        runtime(":resources:1.2.RC2") { excludes "hibernate"}

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

		runtime(":twitter-bootstrap:2.2.2") { excludes 'svn' }
		runtime(":less-resources:1.3.0.2") { excludes 'svn' }
        runtime(":coffeescript-resources:0.3.2") { excludes "hibernate"}
		runtime(":handlebars-resources:0.3.1") { excludes "hibernate" }
		//runtime ":redis:1.3.2"
		runtime(":mongodb:1.1.0.GA") { excludes "hibernate" }
        compile(":avatar:0.6.3") { excludes "hibernate" }
        build(":tomcat:$grailsVersion") { excludes "hibernate" }
		compile(":spring-security-core:1.2.7.3") { excludes "hibernate" }
        runtime(":cache:1.0.1"){ excludes "hibernate" }
		runtime(":spock:0.7") { excludes "spock-grails-support", "hibernate" }
		compile(":spring-security-core:1.2.4")
		compile(":spring-social-core:0.1.31")
		compile(":spring-social-twitter:0.1.31")
		compile(":spring-social-facebook:0.1.32")
		compile(":spring-social-linked-in:0.1.31")
    }
}
