/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0;
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.gradle.jena;

import jena.schemagen;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SchemaGen extends SourceTask {

    private static final Logger logger = LoggerFactory.getLogger(SchemaGen.class);

    private File outputDirectory;
    private String packageName;
    private String classNameSuffix;
    private boolean inference;
    private boolean ontology;
    private boolean nostrict;

    @OutputDirectory
    public File getOutputDirectory() {
        return outputDirectory;
    }
    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
    public void outputDirectory(File outputDirectory) {
        setOutputDirectory(outputDirectory);
    }

    @Input
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public void packageName(String packageName) {
        setPackageName(packageName);
    }

    @Input
    @Optional
    public String getClassNameSuffix() {
        return classNameSuffix;
    }
    public void setClassNameSuffix(String classNameSuffix) {
        this.classNameSuffix = classNameSuffix;
    }
    public void classNameSuffix(String classNameSuffix) {
        setClassNameSuffix(classNameSuffix);
    }

    @Input
    public boolean isInference() {
        return inference;
    }
    public void setInference(boolean inference) {
        this.inference = inference;
    }
    public void inference(boolean inference) {
        setInference(inference);
    }

    @Input
    public boolean isOntology() {
        return ontology;
    }
    public void setOntology(boolean ontology) {
        this.ontology = ontology;
    }
    public void ontology(boolean ontology) {
        setOntology(ontology);
    }

    @Input
    public boolean isNostrict() {
        return nostrict;
    }
    public void setNostrict(boolean nostrict) {
        this.nostrict = nostrict;
    }
    public void nostrict(boolean nostrict) {
        setNostrict(nostrict);
    }

    @TaskAction
    public void exec() {

        List<String> options = new ArrayList<String>();
        for(File inputFile: getSource()) {
            options.clear();

            options.add("-i");
            options.add(inputFile.getAbsolutePath());

            options.add("-o");
            options.add(getOutputDirectory().getAbsolutePath());

            options.add("--package");
            options.add(getPackageName());

            if(getClassNameSuffix() != null) {
                options.add("--classnamesuffix");
                options.add(getClassNameSuffix());
            }
            if(isInference()) {
                options.add("--inference");
            }
            if(isOntology()) {
                options.add("--ontology");
            }
            if(isNostrict()) {
                options.add("--nostrict");
            }

            logger.info("Excecuting schemagen: " + options);

            // only go() will throw exception but it's protected
            try {
                new schemagen() {
                    @Override
                    protected void go(SchemagenOptions options) {
                        super.go(options);
                    }
                }.go(new schemagen.SchemagenOptionsImpl(options.toArray(new String[options.size()])));
            } catch (schemagen.SchemagenException e) {
                throw new GradleException("Error while generating schema", e);
            }
        }
    }
}
