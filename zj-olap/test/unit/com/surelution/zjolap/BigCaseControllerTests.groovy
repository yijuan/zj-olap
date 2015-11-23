package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(BigCaseController)
@Mock(BigCase)
class BigCaseControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bigCase/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bigCaseInstanceList.size() == 0
        assert model.bigCaseInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bigCaseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bigCaseInstance != null
        assert view == '/bigCase/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bigCase/show/1'
        assert controller.flash.message != null
        assert BigCase.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bigCase/list'

        populateValidParams(params)
        def bigCase = new BigCase(params)

        assert bigCase.save() != null

        params.id = bigCase.id

        def model = controller.show()

        assert model.bigCaseInstance == bigCase
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bigCase/list'

        populateValidParams(params)
        def bigCase = new BigCase(params)

        assert bigCase.save() != null

        params.id = bigCase.id

        def model = controller.edit()

        assert model.bigCaseInstance == bigCase
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bigCase/list'

        response.reset()

        populateValidParams(params)
        def bigCase = new BigCase(params)

        assert bigCase.save() != null

        // test invalid parameters in update
        params.id = bigCase.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bigCase/edit"
        assert model.bigCaseInstance != null

        bigCase.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bigCase/show/$bigCase.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bigCase.clearErrors()

        populateValidParams(params)
        params.id = bigCase.id
        params.version = -1
        controller.update()

        assert view == "/bigCase/edit"
        assert model.bigCaseInstance != null
        assert model.bigCaseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bigCase/list'

        response.reset()

        populateValidParams(params)
        def bigCase = new BigCase(params)

        assert bigCase.save() != null
        assert BigCase.count() == 1

        params.id = bigCase.id

        controller.delete()

        assert BigCase.count() == 0
        assert BigCase.get(bigCase.id) == null
        assert response.redirectedUrl == '/bigCase/list'
    }
}
