/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2019. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.core.filesystem;

import com.google.common.io.ByteStreams;
import dan200.computercraft.api.filesystem.IMount;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.*;

@SuppressWarnings( "deprecation" )
public class JarMountTest
{
    private static final File ZIP_FILE = new File( "test-files/jar-mount.zip" );

    @BeforeClass
    public static void before() throws IOException
    {
        if( ZIP_FILE.exists() ) return;
        ZIP_FILE.getParentFile().mkdirs();

        try( ZipOutputStream stream = new ZipOutputStream( new FileOutputStream( ZIP_FILE ) ) )
        {
            stream.putNextEntry( new ZipEntry( "dir/" ) );
            stream.closeEntry();

            stream.putNextEntry( new ZipEntry( "dir/file.lua" ) );
            stream.write( "print('testing')".getBytes( StandardCharsets.UTF_8 ) );
            stream.closeEntry();
        }
    }

    @Test
    public void mountsDir() throws IOException
    {
        IMount mount = new JarMount( ZIP_FILE, "dir" );
        assertTrue( "Root should be directory", mount.isDirectory( "" ) );
        assertTrue( "File should exist", mount.exists( "file.lua" ) );
    }

    @Test
    public void mountsFile() throws IOException
    {
        IMount mount = new JarMount( ZIP_FILE, "dir/file.lua" );
        assertTrue( "Root should exist", mount.exists( "" ) );
        assertFalse( "Root should be a file", mount.isDirectory( "" ) );
    }

    @Test
    public void opensFileFromFile() throws IOException
    {
        IMount mount = new JarMount( ZIP_FILE, "dir/file.lua" );
        byte[] contents;
        try( InputStream stream = mount.openForRead( "" ) )
        {
            contents = ByteStreams.toByteArray( stream );
        }

        assertEquals( "print('testing')", new String( contents, StandardCharsets.UTF_8 ) );
    }

    @Test
    public void opensFileFromDir() throws IOException
    {
        IMount mount = new JarMount( ZIP_FILE, "dir" );
        byte[] contents;
        try( InputStream stream = mount.openForRead( "file.lua" ) )
        {
            contents = ByteStreams.toByteArray( stream );
        }

        assertEquals( "print('testing')", new String( contents, StandardCharsets.UTF_8 ) );
    }
}