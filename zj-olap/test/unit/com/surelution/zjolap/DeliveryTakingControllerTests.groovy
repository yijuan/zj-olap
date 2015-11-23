package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(DeliveryTakingController)
@Mock(DeliveryTaking)
class DeliveryTakingControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/deliveryTaking/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.deliveryTakingInstanceList.size() == 0
        assert model.deliveryTakingInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.deliveryTakingInstance != null
    }

    void testSave() {
        controller.save()

        assert model.deliveryTakingInstance != null
        assert view == '/deliveryTaking/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/deliveryTaking/show/1'
        assert controller.flash.message != null
        assert DeliveryTaking.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/deliveryTaking/list'

        populateValidParams(params)
        def deliveryTaking = new DeliveryTaking(params)

        assert deliveryTaking.save() != null

        params.id = deliveryTaking.id

        def model = controller.show()

        assert model.deliveryTakingInstance == deliveryTaking
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/deliveryTaking/list'

        populateValidParams(params)
        def deliveryTaking = new DeliveryTaking(params)

        assert deliveryTaking.save() != null

        params.id = deliveryTaking.id

        def model = controller.edit()

        assert model.deliveryTakingInstance == deliveryTaking
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/deliveryTaking/list'

        response.reset()

        populateValidParams(params)
        def deliveryTaking = new DeliveryTaking(params)

        assert deliveryTaking.save() != null

        // test invalid parameters in update
        params.id = deliveryTaking.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/deliveryTaking/edit"
        assert model.deliveryTakingInstance != null

        deliveryTaking.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/deliveryTaking/show/$deliveryTaking.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        deliveryTaking.clearErrors()

        populateValidParams(params)
        params.id = deliveryTaking.id
        params.version = -1
        controller.update()

        assert view == "/deliveryTaking/edit"
        assert model.deliveryTakingInstance != null
        assert model.deliveryTakingInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/deliveryTaking/list'

        response.reset()

        populateValidParams(params)
        def deliveryTaking = new DeliveryTaking(params)

        assert deliveryTaking.save() != null
        assert DeliveryTaking.count() == 1

        params.id = deliveryTaking.id

        controller.delete()

        assert DeliveryTaking.count() == 0
        assert DeliveryTaking.get(deliveryTaking.id) == null
        assert response.redirectedUrl == '/deliveryTaking/list'
    }
}
