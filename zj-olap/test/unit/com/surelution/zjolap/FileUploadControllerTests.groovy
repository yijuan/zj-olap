package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(FileUploadController)
@Mock(FileUpload)
class FileUploadControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/fileUpload/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.fileUploadInstanceList.size() == 0
        assert model.fileUploadInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.fileUploadInstance != null
    }

    void testSave() {
        controller.save()

        assert model.fileUploadInstance != null
        assert view == '/fileUpload/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/fileUpload/show/1'
        assert controller.flash.message != null
        assert FileUpload.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/fileUpload/list'

        populateValidParams(params)
        def fileUpload = new FileUpload(params)

        assert fileUpload.save() != null

        params.id = fileUpload.id

        def model = controller.show()

        assert model.fileUploadInstance == fileUpload
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/fileUpload/list'

        populateValidParams(params)
        def fileUpload = new FileUpload(params)

        assert fileUpload.save() != null

        params.id = fileUpload.id

        def model = controller.edit()

        assert model.fileUploadInstance == fileUpload
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/fileUpload/list'

        response.reset()

        populateValidParams(params)
        def fileUpload = new FileUpload(params)

        assert fileUpload.save() != null

        // test invalid parameters in update
        params.id = fileUpload.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/fileUpload/edit"
        assert model.fileUploadInstance != null

        fileUpload.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/fileUpload/show/$fileUpload.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        fileUpload.clearErrors()

        populateValidParams(params)
        params.id = fileUpload.id
        params.version = -1
        controller.update()

        assert view == "/fileUpload/edit"
        assert model.fileUploadInstance != null
        assert model.fileUploadInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/fileUpload/list'

        response.reset()

        populateValidParams(params)
        def fileUpload = new FileUpload(params)

        assert fileUpload.save() != null
        assert FileUpload.count() == 1

        params.id = fileUpload.id

        controller.delete()

        assert FileUpload.count() == 0
        assert FileUpload.get(fileUpload.id) == null
        assert response.redirectedUrl == '/fileUpload/list'
    }
}
