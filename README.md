# Sling Provisioning Model Demo

This project is a demonstration of using the Sling Provisioning Model to create resources in the JCR, create users, create service users, and set ACLs in an AEM project.

This project was created and tested with AEM 6.4.

## Modules

The main parts of the project are:

### core
 * A Java bundle containing a Sling Model and an OSGi service that reads the JCR using the newly created service account.
 * The Sling Provisioning Model to create the service user is at [/core/src/main/provisioning/model.txt](/core/src/main/provisioning/model.txt).

### ui.apps
 * A single AEM component backed by the Sling Model in core to display the results read by the newly created service account.
 * An OSGi configuration for the Service User Mapper representing the newly configured service account.

### ui.content
 * A single page with the AEM component displaying the results read by the newly created service account.

### sling-provisioning-model
 * A sub-project content package that prepares AEM to work with the [Apache Sling Installer Provisioning Model Support](https://github.com/apache/sling-org-apache-sling-installer-factory-model) project.
 * A sub-project that clones the [Apache Sling Installer Provisioning Model Support](https://github.com/apache/sling-org-apache-sling-installer-factory-model) Github repo, builds it and installs it.

## How to build

To initialize a running AEM instance to accept Sling Provisioning Model files, you must first run

    mvn clean install -PautoInstallSlingProvisioningModel

If you have a running AEM instance you can build and package the whole project and deploy into AEM with

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

## Verify the Results

After first building with the `autoInstallSlingProvisioningModel` and then the `autoInstallPackage` profiles, you will be able to verify that:

 * Service user `sample-service-user` exists by navigating to [User Management](http://localhost:4502/libs/granite/security/content/useradmin.html)
 * User `sample-regular-user` exists by navigating to [User Management](http://localhost:4502/libs/granite/security/content/useradmin.html)
 * [/content/slingprovisioningmodeldemo-newpage.html](http://localhost:4502/content/slingprovisioningmodeldemo-newpage.html) was created by the provisioning model (a blank page)
 * [/content/slingprovisioningmodeldemo.html](http://localhost:4502/content/slingprovisioningmodeldemo.html) displays the correct text read by the newly created service user
 * `sample-service-account` has read permissions for [/conf/global/settings/workflow](http://localhost:4502/crx/de/index.jsp#/conf/global/settings/workflow)
 * `sample-service-account` has read and write permissions for[/content/slingprovisioningmodeldemo-newpage](http://localhost:4502/crx/de/index.jsp#/content/slingprovisioningmodeldemo-newpage)
 * `sample-user-account` has read permissions for[/content/slingprovisioningmodeldemo-newpage](http://localhost:4502/crx/de/index.jsp#/content/slingprovisioningmodeldemo-newpage)

## Further Information

* [Repository Initalization](https://sling.apache.org/documentation/bundles/repository-initialization.html)
* [Apache Sling SlingStart Maven Plugin](https://sling.apache.org/components/slingstart-maven-plugin/)
* [The Apache Sling Provisioning Model and Apache SlingStart](https://sling.apache.org/documentation/development/slingstart.html)

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
