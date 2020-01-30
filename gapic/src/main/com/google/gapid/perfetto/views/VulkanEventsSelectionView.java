package com.google.gapid.perfetto.views;

import static com.google.gapid.widgets.Widgets.createTreeColumn;
import static com.google.gapid.widgets.Widgets.createTreeViewer;
import static com.google.gapid.widgets.Widgets.packColumns;

import com.google.gapid.perfetto.models.VulkanEventTrack;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Displays information about a list of selected vulkan events.
 */
public class VulkanEventsSelectionView extends Composite {
  public VulkanEventsSelectionView(Composite parent, VulkanEventTrack.Slices sel) {
    super(parent, SWT.NONE);
    setLayout(new FillLayout());

    TreeViewer viewer = createTreeViewer(this, SWT.NONE);
    viewer.getTree().setHeaderVisible(true);
    viewer.setContentProvider(new ITreeContentProvider() {
      @Override
      public Object[] getElements(Object inputElement) {
        return sel.slices.toArray();
      }

      @Override
      public boolean hasChildren(Object element) {
        return false;
      }

      @Override
      public Object getParent(Object element) {
        return null;
      }

      @Override
      public Object[] getChildren(Object element) {
        return null;
      }
    });
    viewer.setLabelProvider(new LabelProvider());

    createTreeColumn(viewer, "Slice ID", e -> Long.toString(n(e).id));
    createTreeColumn(viewer, "Start Time", e -> Long.toString(n(e).time));
    createTreeColumn(viewer, "Duration", e -> Long.toString(n(e).dur));
    createTreeColumn(viewer, "Event Name", e -> n(e).name);
    createTreeColumn(viewer, "Submission ID", e -> Long.toString(n(e).submissionId));
    viewer.setInput(sel);
    packColumns(viewer.getTree());
  }

  protected static VulkanEventTrack.Slice n(Object o) {
    return (VulkanEventTrack.Slice)o;
  }
}
