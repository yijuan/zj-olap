package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(ThidrFactorTypeController)
@Mock(ThidrFactorType)
class ThidrFactorTypeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/thidrFactorType/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.thidrFactorTypeInstanceList.size() == 0
        assert model.thidrFactorTypeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.thidrFactorTypeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.thidrFactorTypeInstance != null
        assert view == '/thidrFactorType/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/thidrFactorType/show/1'
        assert controller.flash.message != null
        assert ThidrFactorType.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactorType/list'

        populateValidParams(params)
        def thidrFactorType = new ThidrFactorType(params)

        assert thidrFactorType.save() != null

        params.id = thidrFactorType.id

        def model = controller.show()

        assert model.thidrFactorTypeInstance == thidrFactorType
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactorType/list'

        populateValidParams(params)
        def thidrFactorType = new ThidrFactorType(params)

        assert thidrFactorType.save() != null

        params.id = thidrFactorType.id

        def model = controller.edit()

        assert model.thidrFactorTypeInstance == thidrFactorType
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactorType/list'

        response.reset()

        populateValidParams(params)
        def thidrFactorType = new ThidrFactorType(params)

        assert thidrFactorType.save() != null

        // test invalid parameters in update
        params.id = thidrFactorType.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/thidrFactorType/edit"
        assert model.thidrFactorTypeInstance != null

        thidrFactorType.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/thidrFactorType/show/$thidrFactorType.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        thidrFactorType.clearErrors()

        populateValidParams(params)
        params.id = thidrFactorType.id
        params.version = -1
        controller.update()

        assert view == "/thidrFactorType/edit"
        assert model.thidrFactorTypeInstance != null
        assert model.thidrFactorTypeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactorType/list'

        response.reset()

        populateValidParams(params)
        def thidrFactorType = new ThidrFactorType(params)

        assert thidrFactorType.save() != null
        assert ThidrFactorType.count() == 1

        params.id = thidrFactorType.id

        controller.delete()

        assert ThidrFactorType.count() == 0
        assert ThidrFactorType.get(thidrFactorType.id) == null
        assert response.redirectedUrl == '/thidrFactorType/list'
    }
}
