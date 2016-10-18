scms {

    excludes = ['build.sh', 'build/**', 'Makefile', 'readme.md']

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