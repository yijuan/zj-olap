package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(TankFarmController)
@Mock(TankFarm)
class TankFarmControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/tankFarm/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.tankFarmInstanceList.size() == 0
        assert model.tankFarmInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.tankFarmInstance != null
    }

    void testSave() {
        controller.save()

        assert model.tankFarmInstance != null
        assert view == '/tankFarm/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/tankFarm/show/1'
        assert controller.flash.message != null
        assert TankFarm.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/tankFarm/list'

        populateValidParams(params)
        def tankFarm = new TankFarm(params)

        assert tankFarm.save() != null

        params.id = tankFarm.id

        def model = controller.show()

        assert model.tankFarmInstance == tankFarm
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/tankFarm/list'

        populateValidParams(params)
        def tankFarm = new TankFarm(params)

        assert tankFarm.save() != null

        params.id = tankFarm.id

        def model = controller.edit()

        assert model.tankFarmInstance == tankFarm
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/tankFarm/list'

        response.reset()

        populateValidParams(params)
        def tankFarm = new TankFarm(params)

        assert tankFarm.save() != null

        // test invalid parameters in update
        params.id = tankFarm.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/tankFarm/edit"
        assert model.tankFarmInstance != null

        tankFarm.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/tankFarm/show/$tankFarm.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        tankFarm.clearErrors()

        populateValidParams(params)
        params.id = tankFarm.id
        params.version = -1
        controller.update()

        assert view == "/tankFarm/edit"
        assert model.tankFarmInstance != null
        assert model.tankFarmInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/tankFarm/list'

        response.reset()

        populateValidParams(params)
        def tankFarm = new TankFarm(params)

        assert tankFarm.save() != null
        assert TankFarm.count() == 1

        params.id = tankFarm.id

        controller.delete()

        assert TankFarm.count() == 0
        assert TankFarm.get(tankFarm.id) == null
        assert response.redirectedUrl == '/tankFarm/list'
    }
}
