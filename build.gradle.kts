plugins {
    id("java")
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.demo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // querydsl
    implementation("io.github.openfeign.querydsl:querydsl-jpa:6.10.1")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:6.10.1:jpa")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

/*
 * QueryDSL Build Options
 */
//val querydslDir = "${layout.projectDirectory}/build/generated/querydsl"
//
//sourceSets {
//    getByName("main").java.srcDirs(querydslDir)
//}
//
//tasks.withType<JavaCompile> {
//    options.generatedSourceOutputDirectory = file(querydslDir)
//}
//
//tasks.named("clean") {
//    doLast {
//        file(querydslDir).deleteRecursively()
//    }
//}
val querydslDir = layout.buildDirectory.dir("generated/querydsl").get().asFile

sourceSets {
    getByName("main").java.srcDir(querydslDir)
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(querydslDir)
}

tasks.named<Delete>("clean") {
    delete(querydslDir)
}