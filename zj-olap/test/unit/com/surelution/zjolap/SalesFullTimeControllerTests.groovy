package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(SalesFullTimeController)
@Mock(SalesFullTime)
class SalesFullTimeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/salesFullTime/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.salesFullTimeInstanceList.size() == 0
        assert model.salesFullTimeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.salesFullTimeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.salesFullTimeInstance != null
        assert view == '/salesFullTime/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/salesFullTime/show/1'
        assert controller.flash.message != null
        assert SalesFullTime.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/salesFullTime/list'

        populateValidParams(params)
        def salesFullTime = new SalesFullTime(params)

        assert salesFullTime.save() != null

        params.id = salesFullTime.id

        def model = controller.show()

        assert model.salesFullTimeInstance == salesFullTime
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/salesFullTime/list'

        populateValidParams(params)
        def salesFullTime = new SalesFullTime(params)

        assert salesFullTime.save() != null

        params.id = salesFullTime.id

        def model = controller.edit()

        assert model.salesFullTimeInstance == salesFullTime
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/salesFullTime/list'

        response.reset()

        populateValidParams(params)
        def salesFullTime = new SalesFullTime(params)

        assert salesFullTime.save() != null

        // test invalid parameters in update
        params.id = salesFullTime.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/salesFullTime/edit"
        assert model.salesFullTimeInstance != null

        salesFullTime.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/salesFullTime/show/$salesFullTime.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        salesFullTime.clearErrors()

        populateValidParams(params)
        params.id = salesFullTime.id
        params.version = -1
        controller.update()

        assert view == "/salesFullTime/edit"
        assert model.salesFullTimeInstance != null
        assert model.salesFullTimeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/salesFullTime/list'

        response.reset()

        populateValidParams(params)
        def salesFullTime = new SalesFullTime(params)

        assert salesFullTime.save() != null
        assert SalesFullTime.count() == 1

        params.id = salesFullTime.id

        controller.delete()

        assert SalesFullTime.count() == 0
        assert SalesFullTime.get(salesFullTime.id) == null
        assert response.redirectedUrl == '/salesFullTime/list'
    }
}
