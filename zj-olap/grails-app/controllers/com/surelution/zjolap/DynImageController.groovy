package com.surelution.zjolap


class DynImageController {

    def file(Long id) {
		def fileUpload= FileUpload.get(id)
        def path = grailsApplication.config.file.importer.file.location
		def uuid =fileUpload.fileUUID
		println "${path}/${uuid}"
        File file = new File("${path}/${uuid}")
        def os = response.outputStream
        os << file.bytes
        os.flush()
        return
    }

   
}
