
module.exports = function(grunt) {
    grunt.initConfig({
        jshint: {
            files: ['lib/**/*.js']
        },

    });

    grunt.loadNpmTasks('grunt-contrib-jshint');

    grunt.registerTask('default', ['jshint']);
};
