package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(BranchGroupController)
@Mock(BranchGroup)
class BranchGroupControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/branchGroup/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.branchGroupInstanceList.size() == 0
        assert model.branchGroupInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.branchGroupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.branchGroupInstance != null
        assert view == '/branchGroup/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/branchGroup/show/1'
        assert controller.flash.message != null
        assert BranchGroup.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/branchGroup/list'

        populateValidParams(params)
        def branchGroup = new BranchGroup(params)

        assert branchGroup.save() != null

        params.id = branchGroup.id

        def model = controller.show()

        assert model.branchGroupInstance == branchGroup
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/branchGroup/list'

        populateValidParams(params)
        def branchGroup = new BranchGroup(params)

        assert branchGroup.save() != null

        params.id = branchGroup.id

        def model = controller.edit()

        assert model.branchGroupInstance == branchGroup
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/branchGroup/list'

        response.reset()

        populateValidParams(params)
        def branchGroup = new BranchGroup(params)

        assert branchGroup.save() != null

        // test invalid parameters in update
        params.id = branchGroup.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/branchGroup/edit"
        assert model.branchGroupInstance != null

        branchGroup.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/branchGroup/show/$branchGroup.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        branchGroup.clearErrors()

        populateValidParams(params)
        params.id = branchGroup.id
        params.version = -1
        controller.update()

        assert view == "/branchGroup/edit"
        assert model.branchGroupInstance != null
        assert model.branchGroupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/branchGroup/list'

        response.reset()

        populateValidParams(params)
        def branchGroup = new BranchGroup(params)

        assert branchGroup.save() != null
        assert BranchGroup.count() == 1

        params.id = branchGroup.id

        controller.delete()

        assert BranchGroup.count() == 0
        assert BranchGroup.get(branchGroup.id) == null
        assert response.redirectedUrl == '/branchGroup/list'
    }
}
