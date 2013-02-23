modules = {
    application {
    	dependsOn 'bootstrap'
    	resource url: 'css/font-awesome.css'
        resource url: 'less/application.less', attrs: [rel: "stylesheet/less", type:'css']
        resource url: 'coffee/app.coffee'
		
		resource url: 'js/underscore.js'
		resource url: 'js/backbone.js'
		
		resource url: 'coffee/modules/mercury/views/Mercury.QuestionEdit.coffee'
		
		resource url: 'coffee/modules/mercury/routers/Mercury.Router.coffee'
		
    }
}