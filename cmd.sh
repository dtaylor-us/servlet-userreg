#!/bin/bash

export DATABASE_URL=$(heroku config | grep CLEARDB_DATABASE_URL | sed 's/^.*: //')
export HEROKU_URL=$(heroku apps:info -s  | grep web_url | cut -d= -f2)

clean() {
    clear
    echo "**********************************************************************************************************"
    echo "     APP CONFIG FILE: Cleaning project..."
    echo "**********************************************************************************************************"
    mvn clean
}

compile() {
    clear
    echo "**********************************************************************************************************"
    echo "     APP CONFIG FILE: Compiling.."
    echo "**********************************************************************************************************"
    mvn compile
}

install() {
    clear
    echo "**********************************************************************************************************"
    echo "     APP CONFIG FILE: Running Install..."
    echo "**********************************************************************************************************"
    mvn install
}

deploy_war() {
    clear
    echo "**********************************************************************************************************"
    echo "     APP CONFIG FILE: Deploying war to $HEROKU_URL..."
    echo "**********************************************************************************************************"
    mvn heroku:deploy-war
}

migrate_schema() {
    clear
    echo "**********************************************************************************************************"
    echo "     APP CONFIG FILE: Initializing Migration..."
    echo "**********************************************************************************************************"
    mvn initialize flyway:migrate
}

start_server() {
    clear
    echo "***********************************************************************************************************"
    echo "      Starting Tomcat Server..."
    echo "***********************************************************************************************************"
    mvn tomcat7:run-war
}

case $1 in
    clean)
        clean
        ;;
    compile)
        compile
        ;;
    install)
        install
        ;;
    deploy)
        deploy_war
        ;;
    migrate)
        migrate_schema
        ;;
    run)
        clean
        compile
        install
        start_server
        ;;

    esac
exit 0