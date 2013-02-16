modules = {
    application {
    	dependsOn 'bootstrap'
    	resource url: 'css/font-awesome.css'
        resource url: 'less/application.less', attrs: [rel: "stylesheet/less", type:'css']
        resource url: 'coffee/app.coffee'
    }
}