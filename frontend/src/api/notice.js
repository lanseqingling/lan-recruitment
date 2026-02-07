import request from './request'

export function listNotices() {
  return request.get('/api/public/notice/list')
}

export function adminListNotices(status) {
  return request.get('/api/admin/notice/list', { params: { status } })
}

export function adminSaveNotice(payload) {
  return request.post('/api/admin/notice/save', payload)
}

export function adminDeleteNotice(id) {
  return request.post('/api/admin/notice/delete', { id })
}

