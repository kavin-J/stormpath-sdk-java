def baseExcludes = ['build.sh', 'build/**', 'Makefile', 'readme.md']

scms {
    excludes = baseExcludes

    patterns {

        '**/*.rst' {
            renderer = 'velocity' //ordinarily only looks for files with vtl extension
            outputFileExtension = 'rst' //ordinarily html
        }

    }
}

environments {

    def springExcludes = baseExcludes + ['source/access-control.rst', 'source/appendix/web-stormpath-properties.rst']

    servlet {
        scms {
            excludes = baseExcludes +
                        //the following files are not relevant for the servlet docs.  We exclude them here
                        //so the sphinx parser doesn't warn us during rendering, e.g.
                        //    WARNING: document isn't included in any toctree
                        ['source/appendix/default-stormpath-properties.rst',
                        'source/appendix/spring-boot-core-properties.rst',
                        'source/appendix/spring-boot-web-properties.rst',
                        'source/appendix/forgot-password.rst',
                        'source/appendix/change-password.rst',
                        'source/appendix/head.rst',
                        'source/appendix/login.rst',
                        'source/appendix/register.rst',
                        'source/appendix/verify.rst']
            model {
                servlet = true
            }
        }
    }

    sczuul {
        scms {
            excludes = springExcludes
            model {
                sczuul = true
            }
        }
    }

    springboot {
        scms {
            excludes = springExcludes
            model {
                springboot = true
            }
        }
    }
}