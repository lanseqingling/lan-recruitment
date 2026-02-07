import request from './request'

export function listTags(tagType) {
  return request.get('/api/public/tag/list', { params: { tagType } })
}

export function adminListTags(params) {
  return request.get('/api/admin/tag/list', { params })
}

export function adminSaveTag(payload) {
  return request.post('/api/admin/tag/save', payload)
}

export function adminDeleteTag(id) {
  return request.post('/api/admin/tag/delete', { id })
}
