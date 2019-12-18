
UBERJAR=./target/coming-postal-0.1.0-SNAPSHOT-standalone.jar

$(UBERJAR):
	lein clean
	lein uberjar

target/coming-post: $(UBERJAR)
	/usr/lib/jvm/default-runtime/jre/bin/native-image \
		--no-server \
		--report-unsupported-elements-at-runtime \
		--initialize-at-build-time=javax.net.ssl.SSLContext \
		--enable-url-protocols=http,https \
		--enable-http \
		--enable-https \
		--enable-all-security-services \
		-H:Name=./target/coming-postal \
		-H:+ReportExceptionStackTraces \
		--no-fallback \
		-jar $(UBERJAR)
