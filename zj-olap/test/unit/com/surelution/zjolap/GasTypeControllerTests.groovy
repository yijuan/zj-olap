package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(GasTypeController)
@Mock(GasType)
class GasTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/gasType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.gasTypeInstanceList.size() == 0
        assert model.gasTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.gasTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.gasTypeInstance != null
        assert view == '/gasType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/gasType/show/1'
        assert controller.flash.message != null
        assert GasType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/gasType/list'

        populateValidParams(params)
        def gasType = new GasType(params)

        assert gasType.save() != null

        params.id = gasType.id

        def model = controller.show()

        assert model.gasTypeInstance == gasType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/gasType/list'

        populateValidParams(params)
        def gasType = new GasType(params)

        assert gasType.save() != null

        params.id = gasType.id

        def model = controller.edit()

        assert model.gasTypeInstance == gasType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/gasType/list'

        response.reset()

        populateValidParams(params)
        def gasType = new GasType(params)

        assert gasType.save() != null

        // test invalid parameters in update
        params.id = gasType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/gasType/edit"
        assert model.gasTypeInstance != null

        gasType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/gasType/show/$gasType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        gasType.clearErrors()

        populateValidParams(params)
        params.id = gasType.id
        params.version = -1
        controller.update()

        assert view == "/gasType/edit"
        assert model.gasTypeInstance != null
        assert model.gasTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/gasType/list'

        response.reset()

        populateValidParams(params)
        def gasType = new GasType(params)

        assert gasType.save() != null
        assert GasType.count() == 1

        params.id = gasType.id

        controller.delete()

        assert GasType.count() == 0
        assert GasType.get(gasType.id) == null
        assert response.redirectedUrl == '/gasType/list'
    }
}
