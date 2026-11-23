self.addEventListener('push', function(event) {
  const jsonData = event.data.json();
  const options = {
    body: jsonData.data.message,
    icon: 'favicon.ico',
    badge: 'favicon.ico'
  };
  console.log('FCM Push Data:', jsonData);
  event.waitUntil(
    self.registration.showNotification('커뮤니티 피드 서비스 알림', options)
  );
});