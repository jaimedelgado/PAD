module.exports = function(grunt) {
	grunt.initConfig({
		bb_generate: {
			options: {
				appname       : "NeedlePOP",
				appsrc        : "js/backbone",
				routersrc     : "js/backbone/routers/",
				modelsrc      : "js/backbone/models/",
				viewsrc       : "js/backbone/views/",
				collectionsrc : "js/backbone/collections/",
				templatesrc   : "js/backbone/templates/"
			},
			router:{},
			view:{},
			collection:{},
			model:{},
			template:{}
		}
	});


	grunt.loadNpmTasks('grunt-bb-generate');
};