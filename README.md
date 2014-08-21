# Gradle plugin for Apache Jena

This is a gradle plugin that wraps some jena tools as gradle tasks.

## schemagen

Generates Java files from RDFS or OWL schema. See also the
[schemagen documentation](https://jena.apache.org/documentation/tools/schemagen.html).

Example:

    buildscript {
        repositories {
            mavenLocal()
        }
        dependencies {
            classpath("hu.webhejj.gradle:gradle-jena-plugin:1.0.0-SNAPSHOT")
        }
    }

    task ontgen(type: hu.webhejj.gradle.jena.SchemaGen) {
        source = fileTree("src/main/resources/ontology")
        outputDirectory = file("build/ontology")
        packageName = "hu.webhejj.project.ontology"
        classNameSuffix = "Ontology"
        ontology = true
        inference = true
        nostrict = true
    }
    
    
## License    
    
Copyright 2014 Gergely Nagy <greg@webhejj.hu>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.