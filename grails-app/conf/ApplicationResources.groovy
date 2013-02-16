modules = {
    application {
        resource url:'js/application.js'
    	dependsOn 'bootstrap'
        resource url:'less/application.less', attrs: [rel: "stylesheet/less", type:'css']
    }
}