package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(SalingTypeController)
@Mock(SalingType)
class SalingTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/salingType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.salingTypeInstanceList.size() == 0
        assert model.salingTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.salingTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.salingTypeInstance != null
        assert view == '/salingType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/salingType/show/1'
        assert controller.flash.message != null
        assert SalingType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/salingType/list'

        populateValidParams(params)
        def salingType = new SalingType(params)

        assert salingType.save() != null

        params.id = salingType.id

        def model = controller.show()

        assert model.salingTypeInstance == salingType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/salingType/list'

        populateValidParams(params)
        def salingType = new SalingType(params)

        assert salingType.save() != null

        params.id = salingType.id

        def model = controller.edit()

        assert model.salingTypeInstance == salingType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/salingType/list'

        response.reset()

        populateValidParams(params)
        def salingType = new SalingType(params)

        assert salingType.save() != null

        // test invalid parameters in update
        params.id = salingType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/salingType/edit"
        assert model.salingTypeInstance != null

        salingType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/salingType/show/$salingType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        salingType.clearErrors()

        populateValidParams(params)
        params.id = salingType.id
        params.version = -1
        controller.update()

        assert view == "/salingType/edit"
        assert model.salingTypeInstance != null
        assert model.salingTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/salingType/list'

        response.reset()

        populateValidParams(params)
        def salingType = new SalingType(params)

        assert salingType.save() != null
        assert SalingType.count() == 1

        params.id = salingType.id

        controller.delete()

        assert SalingType.count() == 0
        assert SalingType.get(salingType.id) == null
        assert response.redirectedUrl == '/salingType/list'
    }
}
