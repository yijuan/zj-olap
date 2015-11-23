package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(ThidrFactorController)
@Mock(ThidrFactor)
class ThidrFactorControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/thidrFactor/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.thidrFactorInstanceList.size() == 0
        assert model.thidrFactorInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.thidrFactorInstance != null
    }

    void testSave() {
        controller.save()

        assert model.thidrFactorInstance != null
        assert view == '/thidrFactor/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/thidrFactor/show/1'
        assert controller.flash.message != null
        assert ThidrFactor.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactor/list'

        populateValidParams(params)
        def thidrFactor = new ThidrFactor(params)

        assert thidrFactor.save() != null

        params.id = thidrFactor.id

        def model = controller.show()

        assert model.thidrFactorInstance == thidrFactor
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactor/list'

        populateValidParams(params)
        def thidrFactor = new ThidrFactor(params)

        assert thidrFactor.save() != null

        params.id = thidrFactor.id

        def model = controller.edit()

        assert model.thidrFactorInstance == thidrFactor
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactor/list'

        response.reset()

        populateValidParams(params)
        def thidrFactor = new ThidrFactor(params)

        assert thidrFactor.save() != null

        // test invalid parameters in update
        params.id = thidrFactor.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/thidrFactor/edit"
        assert model.thidrFactorInstance != null

        thidrFactor.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/thidrFactor/show/$thidrFactor.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        thidrFactor.clearErrors()

        populateValidParams(params)
        params.id = thidrFactor.id
        params.version = -1
        controller.update()

        assert view == "/thidrFactor/edit"
        assert model.thidrFactorInstance != null
        assert model.thidrFactorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/thidrFactor/list'

        response.reset()

        populateValidParams(params)
        def thidrFactor = new ThidrFactor(params)

        assert thidrFactor.save() != null
        assert ThidrFactor.count() == 1

        params.id = thidrFactor.id

        controller.delete()

        assert ThidrFactor.count() == 0
        assert ThidrFactor.get(thidrFactor.id) == null
        assert response.redirectedUrl == '/thidrFactor/list'
    }
}
