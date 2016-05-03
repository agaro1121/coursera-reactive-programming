```
/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/bin/java -Dakka.loglevel=INFO -Dakka.actor.provider=akka.cluster.ClusterActorRefProvider -Dakka.cluster.min-nr-of-members=2 -Didea.launcher.port=7532 "-Didea.launcher.bin.path=/Applications/IntelliJ IDEA.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/lib/tools.jar:/Users/anthonygaro/personal_projects/coursera-reactive-programming/target/scala-2.11/classes:/Users/anthonygaro/.ivy2/cache/org.uncommons.maths/uncommons-maths/jars/uncommons-maths-1.2.2a.jar:/Users/anthonygaro/.ivy2/cache/io.netty/netty/bundles/netty-3.6.6.Final.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-cluster-tools_2.11/jars/akka-cluster-tools_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-cluster-sharding_2.11/jars/akka-cluster-sharding_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-cluster-metrics_2.11/jars/akka-cluster-metrics_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe/config/bundles/config-1.3.0.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-actor_2.11/jars/akka-actor_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-persistence-tck_2.11/jars/akka-persistence-tck_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-persistence_2.11/jars/akka-persistence_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-protobuf_2.11/jars/akka-protobuf_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-testkit_2.11/jars/akka-testkit_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/io.reactivex/rxjava/jars/rxjava-1.0.7.jar:/Users/anthonygaro/.ivy2/cache/io.reactivex/rxscala_2.11/jars/rxscala_2.11-0.24.0.jar:/Users/anthonygaro/.ivy2/cache/junit/junit/jars/junit-4.12.jar:/Users/anthonygaro/.ivy2/cache/org.hamcrest/hamcrest-core/jars/hamcrest-core-1.3.jar:/Users/anthonygaro/.ivy2/cache/org.jsoup/jsoup/jars/jsoup-1.8.1.jar:/Users/anthonygaro/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.11.7.jar:/Users/anthonygaro/.ivy2/cache/org.scala-lang/scala-reflect/jars/scala-reflect-2.11.7.jar:/Users/anthonygaro/.ivy2/cache/org.scala-lang.modules/scala-java8-compat_2.11/bundles/scala-java8-compat_2.11-0.7.0.jar:/Users/anthonygaro/.ivy2/cache/org.scala-lang.modules/scala-xml_2.11/bundles/scala-xml_2.11-1.0.2.jar:/Users/anthonygaro/.ivy2/cache/org.scala-sbt/test-interface/jars/test-interface-1.0.jar:/Users/anthonygaro/.ivy2/cache/org.scalacheck/scalacheck_2.11/jars/scalacheck_2.11-1.13.0.jar:/Users/anthonygaro/.ivy2/cache/org.scalatest/scalatest_2.11/bundles/scalatest_2.11-2.2.6.jar:/Users/anthonygaro/.ivy2/cache/org.slf4j/slf4j-api/jars/slf4j-api-1.7.5.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-cluster_2.11/jars/akka-cluster_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.typesafe.akka/akka-remote_2.11/jars/akka-remote_2.11-2.4.2.jar:/Users/anthonygaro/.ivy2/cache/com.ning/async-http-client/jars/async-http-client-1.7.19.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar" com.intellij.rt.execution.application.AppMain akka.Main week7.cluster.example.ClusterMain
[INFO] [04/27/2016 14:46:18.882] [main] [akka.remote.Remoting] Starting remoting
[INFO] [04/27/2016 14:46:19.419] [main] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://Main@10.145.40.34:2552]
[INFO] [04/27/2016 14:46:19.421] [main] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://Main@10.145.40.34:2552]
[INFO] [04/27/2016 14:46:19.433] [main] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Starting up...
[INFO] [04/27/2016 14:46:19.542] [main] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Registered cluster JMX MBean [akka:type=Cluster]
[INFO] [04/27/2016 14:46:19.542] [main] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Started up successfully
[INFO] [04/27/2016 14:46:19.547] [Main-akka.actor.default-dispatcher-14] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Metrics will be retreived from MBeans, and may be incorrect on some platforms. To increase metric accuracy add the 'sigar.jar' to the classpath and the appropriate platform-specific native libary to 'java.library.path'. Reason: java.lang.ClassNotFoundException: org.hyperic.sigar.Sigar
[INFO] [04/27/2016 14:46:19.550] [Main-akka.actor.default-dispatcher-14] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Metrics collection has started successfully
[INFO] [04/27/2016 14:46:19.564] [Main-akka.actor.default-dispatcher-2] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - No seed-nodes configured, manual cluster join required
Failed to fetch 'http://www.google.com': no nodes available

[INFO] [04/27/2016 14:46:19.580] [Main-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Node [akka.tcp://Main@10.145.40.34:2552] is JOINING, roles []
[INFO] [04/27/2016 14:46:24.870] [Main-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Node [akka.tcp://Main@10.145.40.34:59086] is JOINING, roles []
[INFO] [04/27/2016 14:46:25.587] [Main-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Leader is moving node [akka.tcp://Main@10.145.40.34:2552] to [Up]
[INFO] [04/27/2016 14:46:25.587] [Main-akka.actor.default-dispatcher-4] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Leader is moving node [akka.tcp://Main@10.145.40.34:59086] to [Up]
[WARN] [04/27/2016 14:46:26.634] [Main-akka.remote.default-remote-dispatcher-5] [akka.serialization.Serialization(akka://Main)] Using the default Java serializer for class [week5.lecture4_testing.testactor2.Controller$Check] which is not recommended because of performance implications. Use another serializer or disable this warning using the setting 'akka.actor.warn-about-java-serializer-usage'
Failed to fetch 'http://www.google.com/0': too  many parallel queries

Failed to fetch 'http://www.google.com/1': too  many parallel queries

Failed to fetch 'http://www.google.com/2': too  many parallel queries

Failed to fetch 'http://www.google.com/3': too  many parallel queries

Results for 'http://www.google.com':

http://adwords.blogspot.com/
http://goo.gl/vt4YKT
http://maps.google.com/maps?hl=en&tab=il
http://maps.google.com/maps?hl=en&tab=wl
http://myaccount.google.com/
http://news.google.com/nwshp?hl=en&tab=in
http://news.google.com/nwshp?hl=en&tab=wn
http://support.google.com/accounts/answer/32046?hl=en
http://support.google.com/accounts/bin/answer.py?hl=en&answer=58585
http://support.google.com/bin/static.py?hl=en&ts=1114905&page=ts.cs
http://support.google.com/blogger/answer/41387?hl=en
http://support.google.com/plus/answer/1044503?hl=en
http://support.google.com/plus/bin/static.py?hl=en&page=guide.cs&guide=1257347
http://support.google.com/sites/answer/90598?hl=en
http://support.google.com/websearch/answer/465?hl=en
http://support.google.com/youtube/answer/55759?hl=en
http://translate.google.com/
http://wallet.google.com/files/privacy.html?hl=en
http://www.dataliberation.org/
http://www.google.com
http://www.google.com/
http://www.google.com/ads/admob/?subid=ww-ww-et-bizsol&_adc=eww-ww-et-bizsol&utm_source=Internal&utm_medium=ET&utm_campaign=business-sol-other-sol
http://www.google.com/adsense/start/?subid=WW-EN-ET-bizsol-othersol&utm_source=Internal&utm_medium=ET&utm_campaign=business-sol-other-sol
http://www.google.com/advanced_image_search?hl=en&authuser=0
http://www.google.com/advanced_search?hl=en&authuser=0
http://www.google.com/adwords/express/?utm_source=awx&utm_medium=et&utm_campaign=us-en-et-awx-bizsol&utm_content=othersol
http://www.google.com/chrome/intl/en/more/security.html
http://www.google.com/chrome/intl/en/privacy.html
http://www.google.com/history/optout?hl=en
http://www.google.com/history/optout?hl=en&nzb=1
http://www.google.com/imghp?hl=en&tab=wi
http://www.google.com/intl/en/about.html
http://www.google.com/intl/en/about/
http://www.google.com/intl/en/ads/
http://www.google.com/intl/en/contact/
http://www.google.com/intl/en/landing/2step/
http://www.google.com/intl/en/policies/
http://www.google.com/intl/en/policies/faq/
http://www.google.com/intl/en/policies/privacy/
http://www.google.com/intl/en/policies/privacy/#access
http://www.google.com/intl/en/policies/privacy/#application
http://www.google.com/intl/en/policies/privacy/#content
http://www.google.com/intl/en/policies/privacy/#enforcement
http://www.google.com/intl/en/policies/privacy/#infochoices
http://www.google.com/intl/en/policies/privacy/#infocollect
http://www.google.com/intl/en/policies/privacy/#infosecurity
http://www.google.com/intl/en/policies/privacy/#infosharing
http://www.google.com/intl/en/policies/privacy/#infouse
http://www.google.com/intl/en/policies/privacy/#nosharing
http://www.google.com/intl/en/policies/privacy/#policychanges
http://www.google.com/intl/en/policies/privacy/#products
http://www.google.com/intl/en/policies/privacy/#related
http://www.google.com/intl/en/policies/privacy/archive/
http://www.google.com/intl/en/policies/privacy/example/access-to-your-personal-information.html
http://www.google.com/intl/en/policies/privacy/example/ads-youll-find-most-useful.html
http://www.google.com/intl/en/policies/privacy/example/advertising-services.html
http://www.google.com/intl/en/policies/privacy/example/collect-information.html
http://www.google.com/intl/en/policies/privacy/example/combine-personal-information.html
http://www.google.com/intl/en/policies/privacy/example/connect-with-people.html
http://www.google.com/intl/en/policies/privacy/example/credit-card.html
http://www.google.com/intl/en/policies/privacy/example/develop-new-ones.html
http://www.google.com/intl/en/policies/privacy/example/device-identifiers.html
http://www.google.com/intl/en/policies/privacy/example/device-specific-information.html
http://www.google.com/intl/en/policies/privacy/example/improve-your-user-experience.html
http://www.google.com/intl/en/policies/privacy/example/legal-process.html
http://www.google.com/intl/en/policies/privacy/example/limit-sharing-or-visibility-settings.html
http://www.google.com/intl/en/policies/privacy/example/linked-with-information-about-visits-to-multiple-sites.html
http://www.google.com/intl/en/policies/privacy/example/maintain-services.html
http://www.google.com/intl/en/policies/privacy/example/may-collect-and-process-information.html
http://www.google.com/intl/en/policies/privacy/example/may-not-function-properly.html
http://www.google.com/intl/en/policies/privacy/example/more-relevant-search-results.html
http://www.google.com/intl/en/policies/privacy/example/our-partners.html
http://www.google.com/intl/en/policies/privacy/example/phone-number.html
http://www.google.com/intl/en/policies/privacy/example/protect-google-and-our-users.html
http://www.google.com/intl/en/policies/privacy/example/protect-services.html
http://www.google.com/intl/en/policies/privacy/example/provide-services.html
http://www.google.com/intl/en/policies/privacy/example/removing-your-content.html
http://www.google.com/intl/en/policies/privacy/example/sensors.html
http://www.google.com/intl/en/policies/privacy/example/sharing-with-others.html
http://www.google.com/intl/en/policies/privacy/example/sharing.html
http://www.google.com/intl/en/policies/privacy/example/the-people-who-matter-most.html
http://www.google.com/intl/en/policies/privacy/example/to-make-it-easier-to-share.html
http://www.google.com/intl/en/policies/privacy/example/to-show-trends.html
http://www.google.com/intl/en/policies/privacy/example/view-and-interact-with-our-ads.html
http://www.google.com/intl/en/policies/privacy/example/we-may-share.html
http://www.google.com/intl/en/policies/privacy/example/wifi-access-points-and-cell-towers.html
http://www.google.com/intl/en/policies/privacy/frameworks/
http://www.google.com/intl/en/policies/privacy/google_privacy_policy_en.pdf
http://www.google.com/intl/en/policies/privacy/key-terms/
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-account
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-affiliates
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-application-data-cache
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-browser-storage
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-cookie
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-device
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-info
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-ip
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-personal-info
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-pixel
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-sensitive-categories
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-sensitive-info
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-server-logs
http://www.google.com/intl/en/policies/privacy/key-terms/#toc-terms-unique-device-id
http://www.google.com/intl/en/policies/privacy/partners/
http://www.google.com/intl/en/policies/technologies/
http://www.google.com/intl/en/policies/technologies/ads/
http://www.google.com/intl/en/policies/technologies/cookies/
http://www.google.com/intl/en/policies/technologies/location-data/
http://www.google.com/intl/en/policies/technologies/pattern-recognition/
http://www.google.com/intl/en/policies/technologies/product-privacy/
http://www.google.com/intl/en/policies/technologies/voice/
http://www.google.com/intl/en/policies/technologies/wallet/
http://www.google.com/intl/en/policies/terms/
http://www.google.com/intl/en/policies/terms/#content
http://www.google.com/intl/en/policies/terms/archive/
http://www.google.com/intl/en/safetycenter/
http://www.google.com/intl/en/safetycenter/families/start/
http://www.google.com/language_tools?hl=en&authuser=0
http://www.google.com/preferences?hl=en
http://www.google.com/services/
http://www.google.com/services/#advertise
http://www.google.com/services/#earn-money-from-ads
http://www.google.com/services/#hero
http://www.google.com/services/#measure-learn
http://www.google.com/services/#see-all-solutions
http://www.google.com/services/#work-smarter
http://www.google.com/support/accounts?hl=en
http://www.google.com/trends/
http://www.youtube.com/?hl=en&tab=i1
http://www.youtube.com/?tab=w1
http://www.youtube.com/advertise
http://www.youtube.com/trendsmap
http://www.youtube.com/yt/advertise/?subid=ww-ww-et-v_ads_buis_sol&sourceid=awo&utm_source=embedded_promo&utm_medium=et&utm_campaign=buis_solutions&utm_term=et&utm_content=ww
https://accounts.google.com/AccountChooser?continue=http%3A%2F%2Fwww.google.com%2F&hl=en
https://accounts.google.com/RecoverAccount?continue=http%3A%2F%2Fwww.google.com%2F&ignoreShadow=0&hl=en
https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=http://www.google.com/
https://accounts.google.com/ServiceLogin?hl=en&passive=true&continue=http://www.google.com/imghp%3Fhl%3Den%26tab%3Dwi
https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/?tab%3Dwm&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1
https://accounts.google.com/SignUp?continue=http%3A%2F%2Fwww.google.com%2F&hl=en
https://accounts.google.com/TOS?loc=US&hl=en
https://accounts.google.com/TOS?loc=US&hl=en&privacy=true
https://developers.google.com/?hl=en
https://domains.google.com/about/?hl=en&utm_campaign=2015-q4-na-businesssolutions&utm_medium=ipm&utm_source=businesssolutions&utm_content=ep1
https://drive.google.com/?tab=io
https://drive.google.com/?tab=wo
https://fi.google.com/about/tos/#project-fi-privacy-notice
https://fiber.google.com/legal/privacy.html
https://mail.google.com/mail/?tab=im
https://mail.google.com/mail/?tab=wm
https://myaccount.google.com/?hl=en
https://myaccount.google.com/privacy?hl=en#accounthistory
https://myaccount.google.com/privacy?hl=en#toolsyoucanusenow
https://myaccount.google.com/privacycheckup/1?hl=en
https://myaccount.google.com?hl=en
https://play.google.com/?hl=en&tab=i8
https://play.google.com/?hl=en&tab=w8
https://play.google.com/books/intl/en/privacy.html
https://play.google.com/store?hl=en&tab=w8
https://plus.google.com/+GoogleAds
https://plus.google.com/+GoogleAds/posts?hl=en
https://plus.google.com/+GoogleAnalytics/
https://plus.google.com/116899029375914044550
https://plus.google.com/settings/endorsements
https://privacy.google.com/?hl=en
https://privacy.google.com?hl=en
https://support.google.com
https://support.google.com/?hl=en
https://support.google.com/a/answer/178897?hl=en
https://support.google.com/accounts/answer/112783?hl=en
https://support.google.com/plus/answer/1355890?hl=en
https://support.google.com/policies/troubleshooter/2990837?hl=en
https://support.google.com/websearch/answer/173733?hl=en
https://twitter.com/adwords
https://www.google.com/a/signup/?hl=en&source=bizsol-worksmarter&ga_region=noram&ga_country=us&ga_lang=en#0&utm_source=bizsol_worksmarter&utm_medium=et&utm_campaign=free_trial
https://www.google.com/advanced_search?hl=en&authuser=0
https://www.google.com/adwords/?subid=us-en-et-bizsol
https://www.google.com/analytics/mobile/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=ExploreSolutions
https://www.google.com/analytics/standard/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=ExploreSolutions
https://www.google.com/analytics/tag-manager/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=ExploreSolutions
https://www.google.com/business/?gmbsrc=us-en-et-gs-z-gmb-s-z-l~ser-ctrl-u&subid=us-en-et-bizsol
https://www.google.com/dashboard/?hl=en
https://www.google.com/doubleclick/publishers/small-business/?utm_source=google-biz-sol&utm_medium=et&utm_campaign=biz-sol-all-solutions
https://www.google.com/intl/en/about
https://www.google.com/intl/en/about/products/
https://www.google.com/intl/en/admob/?subid=ww-ww-et-bizsol&_adc=eww-ww-et-bizsol&utm_source=Internal&utm_medium=ET&utm_campaign=business-sol-in-page-txt
https://www.google.com/intl/en/ads/
https://www.google.com/intl/en/adsense/start/?subid=WW-EN-ET-bizsol-inpage&utm_source=Internal&utm_medium=ET&utm_campaign=business-sol-in-page-txt
https://www.google.com/intl/en/adwords/?subid=us-en-et-bizsol
https://www.google.com/intl/en/analytics/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=Footer
https://www.google.com/intl/en/analytics/mobile/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=InPage
https://www.google.com/intl/en/analytics/standard/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=InPage
https://www.google.com/intl/en/options/
https://www.google.com/intl/en/retail/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=googleforretail
https://www.google.com/intl/en/retail/shopping-campaigns/?utm_source=Internal&utm_medium=InPage&utm_campaign=shoppingcampaigns
https://www.google.com/intx/en/work/apps/business/?utm_medium=et&utm_source=bizsol_worksmarter&utm_campaign=footer
https://www.google.com/local/business/add?service=plus&gmbsrc=us-en-et-gs-z-gmb-s-z-l~ser-ctrl-u&ppsrc=GMBLG&utm_campaign=us-en-et-gs-z-gmb-s-z-l~ser-ctrl-u&utm_source=gmb&utm_medium=z&subid=us-en-et-bizsol
https://www.google.com/maps/about/partners/businessview/
https://www.google.com/preferences?hl=en
https://www.google.com/retail/local-inventory-ads/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=localinventoryads
https://www.google.com/retail/shopping-campaigns/?utm_source=Internal&utm_medium=BusinessSolutions&utm_campaign=shoppingcampaigns
https://www.google.com/settings/ads/preferences?hl=en
https://www.google.com/webhp?hl=en&tab=iw
https://www.google.com/work/apps/business/?utm_medium=et&utm_source=bizsol_worksmarter&utm_campaign=explore
https://www.google.com/work/apps/business/products/calendar/?utm_source=bizsol_worksmarter&utm_medium=et&utm_campaign=calendar
https://www.google.com/work/apps/business/products/drive/?utm_source=bizsol_worksmarter&utm_medium=et&utm_campaign=drive
https://www.google.com/work/apps/business/products/gmail/?utm_source=bizsol_worksmarter&utm_medium=et&utm_campaign=gmail
https://www.google.com/work/apps/business/products/hangouts/?utm_source=bizsol_worksmarter&utm_medium=et&utm_campaign=hangouts
https://www.google.com/work/apps/terms/education_privacy.html
https://www.google.com/work/chrome/?utm_medium=et&utm_source=bizsol_worksmarter&utm_campaign=explore
https://www.google.com:443/maps/@?hl=en
https://www.youtube.com/watch?v=48zixFRMJV4
https://www.youtube.com/watch?v=LDKYXDZdFU4
https://www.youtube.com/watch?v=ntrI-Xvzp_Q
https://www.youtube.com/yt/advertise/en/?subid=ww-ww-et-v_ads_buis_sol&sourceid=awo&utm_source=embedded_promo&utm_medium=et&utm_campaign=buis_solutions&utm_term=et&utm_content=ww

[INFO] [04/27/2016 14:46:33.048] [Main-akka.actor.default-dispatcher-2] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Marked address [akka.tcp://Main@10.145.40.34:2552] as [Leaving]
[INFO] [04/27/2016 14:46:33.574] [Main-akka.actor.default-dispatcher-20] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Leader is moving node [akka.tcp://Main@10.145.40.34:2552] to [Exiting]
[INFO] [04/27/2016 14:46:33.577] [Main-akka.actor.default-dispatcher-20] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Shutting down...
[INFO] [04/27/2016 14:46:33.582] [Main-akka.actor.default-dispatcher-20] [akka.cluster.Cluster(akka://Main)] Cluster Node [akka.tcp://Main@10.145.40.34:2552] - Successfully shut down
[INFO] [04/27/2016 14:46:33.595] [Main-akka.actor.default-dispatcher-4] [akka://Main/system/clusterEventBusListener] Message [akka.cluster.ClusterEvent$ClusterShuttingDown$] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/clusterEventBusListener#372000718] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-4] [akka://Main/system/clusterEventBusListener] Message [akka.cluster.ClusterEvent$MemberRemoved] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/clusterEventBusListener#372000718] was not delivered. [2] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-4] [akka://Main/system/cluster/core/daemon/heartbeatSender] Message [akka.cluster.ClusterEvent$MemberRemoved] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/cluster/core/daemon/heartbeatSender#897029367] was not delivered. [3] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-4] [akka://Main/system/clusterEventBusListener] Message [akka.cluster.ClusterEvent$MemberRemoved] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/clusterEventBusListener#372000718] was not delivered. [4] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-4] [akka://Main/system/cluster/core/daemon/heartbeatSender] Message [akka.cluster.ClusterEvent$MemberRemoved] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/cluster/core/daemon/heartbeatSender#897029367] was not delivered. [5] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-2] [akka://Main/system/clusterEventBusListener] Message [akka.cluster.ClusterEvent$LeaderChanged] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/clusterEventBusListener#372000718] was not delivered. [6] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-2] [akka://Main/system/clusterEventBusListener] Message [akka.cluster.ClusterEvent$SeenChanged] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/clusterEventBusListener#372000718] was not delivered. [7] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-2] [akka://Main/system/clusterEventBusListener] Message [akka.cluster.ClusterEvent$ReachabilityChanged] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/clusterEventBusListener#372000718] was not delivered. [8] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-2] [akka://Main/system/cluster/core/publisher] Message [akka.cluster.InternalClusterAction$PublishChanges] from Actor[akka://Main/system/cluster/core/daemon#1973173459] to Actor[akka://Main/system/cluster/core/publisher#-162994638] was not delivered. [9] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.596] [Main-akka.actor.default-dispatcher-2] [akka://Main/system/cluster/core/publisher] Message [akka.cluster.InternalClusterAction$Unsubscribe] from Actor[akka://Main/deadLetters] to Actor[akka://Main/system/cluster/core/publisher#-162994638] was not delivered. [10] dead letters encountered, no more dead letters will be logged. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [04/27/2016 14:46:33.598] [Main-akka.actor.default-dispatcher-3] [akka.tcp://Main@10.145.40.34:2552/user/app-terminator] application supervisor has terminated, shutting down
[INFO] [04/27/2016 14:46:33.601] [Main-akka.remote.default-remote-dispatcher-19] [akka.tcp://Main@10.145.40.34:2552/system/remoting-terminator] Shutting down remote daemon.
[INFO] [04/27/2016 14:46:33.602] [Main-akka.remote.default-remote-dispatcher-19] [akka.tcp://Main@10.145.40.34:2552/system/remoting-terminator] Remote daemon shut down; proceeding with flushing remote transports.
[INFO] [04/27/2016 14:46:33.640] [Main-akka.actor.default-dispatcher-3] [akka.remote.Remoting] Remoting shut down
[INFO] [04/27/2016 14:46:33.641] [Main-akka.remote.default-remote-dispatcher-19] [akka.tcp://Main@10.145.40.34:2552/system/remoting-terminator] Remoting shut down.

Process finished with exit code 0
```