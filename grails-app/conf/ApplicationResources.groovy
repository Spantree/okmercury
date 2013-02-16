modules = {
    application {
    	dependsOn 'bootstrap'
        resource url:'less/application.less', attrs: [rel: "stylesheet/less", type:'css']
        resource url: 'coffee/app.coffee'
    }
}