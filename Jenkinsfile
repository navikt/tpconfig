#!/usr/bin/env groovy
@Library('peon-pipeline') _

node {
    def commitHash
    try {
        cleanWs()

        def version
        stage("checkout") {
            withCredentials([string(credentialsId: 'navikt-ci-oauthtoken', variable: 'GITHUB_OAUTH_TOKEN')]) {
                sh "git init"
                sh "git pull https://${GITHUB_OAUTH_TOKEN}:x-oauth-basic@github.com/navikt/tpconfig.git"
            }

            sh "make bump-version"

            version = sh(script: 'cat VERSION', returnStdout: true).trim()

            commitHash = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
            github.commitStatus("navikt-ci-oauthtoken", "navikt/tpconfig", 'continuous-integration/jenkins', commitHash, 'pending', "Build #${env.BUILD_NUMBER} has started")
        }

        stage("build") {
            tools {
                jdk "openjdk11"
            }
            steps {
                sh "make"
            }
        }

        stage("release") {
            withCredentials([usernamePassword(credentialsId: 'nexusUploader', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                sh "docker login -u ${env.NEXUS_USERNAME} -p ${env.NEXUS_PASSWORD} repo.adeo.no:5443"
            }

            sh "make release"

            withCredentials([string(credentialsId: 'navikt-ci-oauthtoken', variable: 'GITHUB_OAUTH_TOKEN')]) {
                sh "git push --tags https://${GITHUB_OAUTH_TOKEN}@github.com/navikt/tpconfig HEAD:master"
            }
        }

        stage("upload manifest") {
            withCredentials([usernamePassword(credentialsId: 'nexusUploader', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                sh "make manifest"
            }
        }

        stage("deploy preprod") {
            build([
                    job       : 'nais-deploy-pipeline',
                    propagate : true,
                    parameters: [
                            string(name: 'APP', value: "tpconfig"),
                            string(name: 'REPO', value: "navikt/tpconfig"),
                            string(name: 'VERSION', value: version),
                            string(name: 'COMMIT_HASH', value: commitHash),
                            string(name: 'DEPLOY_ENV', value: 'q0')
                    ]
            ])
        }

        stage("deploy prod") {
            build([
                    job       : 'nais-deploy-pipeline',
                    propagate : true,
                    parameters: [
                            string(name: 'APP', value: "tpconfig"),
                            string(name: 'REPO', value: "navikt/tpconfig"),
                            string(name: 'VERSION', value: version),
                            string(name: 'COMMIT_HASH', value: commitHash),
                            string(name: 'DEPLOY_ENV', value: 'p')
                    ]
            ])
        }

        github.commitStatus("navikt-ci-oauthtoken", "navikt/tpconfig", 'continuous-integration/jenkins', commitHash, 'success', "Build #${env.BUILD_NUMBER} has finished")
    } catch (err) {
        github.commitStatus("navikt-ci-oauthtoken", "navikt/tpconfig", 'continuous-integration/jenkins', commitHash, 'failure', "Build #${env.BUILD_NUMBER} has failed")

        throw err
    }
}
