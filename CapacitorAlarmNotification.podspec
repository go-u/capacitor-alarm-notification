
  Pod::Spec.new do |s|
    s.name = 'CapacitorAlarmNotification'
    s.version = '0.0.1'
    s.summary = 'alarm plugin for capacitor'
    s.license = 'MIT'
    s.homepage = 'https://github.com/go-u/capacitor-alarm-notification'
    s.author = 'go'
    s.source = { :git => 'https://github.com/go-u/capacitor-alarm-notification', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end