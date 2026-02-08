import request from './request'

export function getMe() {
  return request.get('/api/common/user/me')
}

export function getProfile() {
  return request.get('/api/common/user/profile')
}

export function updateProfile(profile) {
  return request.post('/api/common/user/profile/update', profile)
}

export function changePassword(oldPassword, newPassword) {
  return request.post('/api/common/user/password/change', { oldPassword, newPassword })
}

export function uploadAvatar(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/api/common/user/avatar/upload', formData)
}
