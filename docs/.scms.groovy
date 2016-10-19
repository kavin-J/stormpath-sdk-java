scms {
    excludes = ['build.sh', 'build/**', 'Makefile', 'readme.md']

    patterns {

        '**/*.rst' {
            renderer = 'velocity' //ordinarily only looks for files with vtl extension
            outputFileExtension = 'rst' //ordinarily html
        }

    }
}

environments {

    servlet {
        scms.model.servlet = true
    }

    sczuul {
        scms.model.sczuul = true
    }

    springboot {
        scms.model.springboot = true
    }

}