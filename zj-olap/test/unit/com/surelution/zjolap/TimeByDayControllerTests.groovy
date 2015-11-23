package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(TimeByDayController)
@Mock(TimeByDay)
class TimeByDayControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/timeByDay/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.timeByDayInstanceList.size() == 0
        assert model.timeByDayInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.timeByDayInstance != null
    }

    void testSave() {
        controller.save()

        assert model.timeByDayInstance != null
        assert view == '/timeByDay/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/timeByDay/show/1'
        assert controller.flash.message != null
        assert TimeByDay.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/timeByDay/list'

        populateValidParams(params)
        def timeByDay = new TimeByDay(params)

        assert timeByDay.save() != null

        params.id = timeByDay.id

        def model = controller.show()

        assert model.timeByDayInstance == timeByDay
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/timeByDay/list'

        populateValidParams(params)
        def timeByDay = new TimeByDay(params)

        assert timeByDay.save() != null

        params.id = timeByDay.id

        def model = controller.edit()

        assert model.timeByDayInstance == timeByDay
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/timeByDay/list'

        response.reset()

        populateValidParams(params)
        def timeByDay = new TimeByDay(params)

        assert timeByDay.save() != null

        // test invalid parameters in update
        params.id = timeByDay.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/timeByDay/edit"
        assert model.timeByDayInstance != null

        timeByDay.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/timeByDay/show/$timeByDay.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        timeByDay.clearErrors()

        populateValidParams(params)
        params.id = timeByDay.id
        params.version = -1
        controller.update()

        assert view == "/timeByDay/edit"
        assert model.timeByDayInstance != null
        assert model.timeByDayInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/timeByDay/list'

        response.reset()

        populateValidParams(params)
        def timeByDay = new TimeByDay(params)

        assert timeByDay.save() != null
        assert TimeByDay.count() == 1

        params.id = timeByDay.id

        controller.delete()

        assert TimeByDay.count() == 0
        assert TimeByDay.get(timeByDay.id) == null
        assert response.redirectedUrl == '/timeByDay/list'
    }
}
